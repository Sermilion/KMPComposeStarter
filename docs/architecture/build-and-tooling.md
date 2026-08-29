# Build and Tooling

## Build System

Shared Gradle behavior lives in `build-logic/convention/`.

Important convention plugins include:

| Plugin | Purpose |
|--------|---------|
| `kmp.application` | Shared app-shell setup for `composeApp` |
| `kmp.library` | Shared KMP library module setup |
| `kmp.compose` | Shared Compose Multiplatform module setup |
| `jvm.library` | Plain JVM module setup (`codegen:viewmodel-inject-processor`) |
| `kmp.kotlininject` | Common KSP DI wiring |
| `kmp.detekt` | Static analysis configuration |
| `kmp.lint` | Android lint configuration |

Keep shared build behavior in these plugins rather than repeating Gradle setup per module.

### What the conventions own

A module build file declares its plugins, its dependencies and nothing else. The conventions own:

- **The Android target.** `compileSdk`, `minSdk`, the host-test compilation and android resources
  are declared once, in `KotlinMultiplatform.kt`. Namespaces are derived from the Gradle path, so
  `:core:data` becomes `com.sermilion.kmpcomposestarter.core.data` and no module repeats one.
  `androidApp` is the single exception: it applies `com.android.application`, whose `android { }`
  block also carries the applicationId, build types and packaging rules that cannot be shared.
- **Compiler arguments.** The shared opt-ins and `-Xexpect-actual-classes` are set on the
  multiplatform extension, which is the only level that also reaches the native compilations.
  `core:data`'s `-Xsuppress-version-warnings` stays module-scoped because it is a Room-alpha
  workaround, not shared policy.
- **Test wiring.** `useJUnitPlatform()` is applied to every `Test` task so Kotest specs are
  discovered in `jvmTest` and `androidHostTest` alike.
- **KSP processors.** The `kmp.kotlininject` convention derives the KSP configuration names from
  the targets a module declares, then adds the kotlin-inject, Anvil and ViewModel processors to
  each. If a module also applies the Room plugin, the Room processor rides the same list. No module
  declares a `ksp*` dependency of its own.

`kspCommonMainMetadata` is deliberately **not** wired. The processors used to be added to it, but
its output was never fed into any compilation, so they ran and produced nothing. The metadata
target is skipped when the configuration list is derived.

## Version Management

All dependency and plugin versions are centralized in `gradle/libs.versions.toml`.

Current headline versions include:

- Kotlin `2.3.10`
- Compose Multiplatform `1.10.2`
- AGP `9.1.0`
- KSP `2.3.6`
- Room 3 `3.0.0-alpha01`
- Gradle `9.4.0`

Prefer updating the version catalog first, then adjusting convention logic only when compatibility actually requires it.

## Toolchains

- Build with JDK 17.
- Keep Android and JVM bytecode targets on Java 11 unless the starter deliberately raises its runtime baseline.
- KSP configurations are derived from the declared targets, which today means Android,
  `iosArm64`, `iosSimulatorArm64` and JVM. Adding or removing a target changes them automatically.

## Repository Policy

Use stable repositories only unless a dependency truly requires something else.

The repository intentionally avoids:

- `mavenLocal()`
- JitPack
- Compose development repositories
- other ad-hoc feeds without a documented need

## AGP 9 Project Structure

The project uses a dedicated Android application module and shared KMP modules on the Android KMP library plugin.

- `androidApp` applies `com.android.application`.
- Shared modules apply `com.android.kotlin.multiplatform.library` through the convention plugins.
- `composeApp` stays responsible for the shared app shell plus iOS and JVM entry points.

This keeps Android app bootstrap separate from shared multiplatform code and avoids the deprecated AGP 9 compatibility bridge.

## Room 3 Lint Caveat

Room 3 `3.0.0-alpha01` currently triggers `RestrictedApi` lint false positives in `core:data` for both generated KSP code and `RoomDatabase` usage under KMP lint tasks.

The module carries exactly one suppression for this: a commented `disable += "RestrictedApi"` in
`core/data/build.gradle.kts`, narrowed for the generated-KSP half by the path-scoped ignore in
`core/data/lint.xml`. Revisit both when the Room pin moves off alpha.

Libraries run lint with `checkDependencies = false`; only `androidApp` aggregates across its
dependencies, so a finding is reported once instead of once per consumer.

## Quality Gates

Main commands:

```bash
./gradlew check
./gradlew detekt
./gradlew spotlessApply
./gradlew :androidApp:assembleDebug
./gradlew :composeApp:linkDebugFrameworkIosArm64
```

`./gradlew check` is the default repository gate. It runs detekt, ktlint, Android lint, the Kotest
suites and the merged Kover report.

The configuration cache is on (`org.gradle.configuration-cache=true` in `gradle.properties`), so a
second `check` reuses the stored configuration instead of re-running it. Build logic that reads
project state at execution time will fail the build rather than silently disabling the cache — keep
new wiring configuration-cache safe.

### Detekt

`config/detekt/detekt.yml` is loaded unconditionally by the `kmp.detekt` convention; a module-level
`detekt.yml` only layers on top of it. `check` depends on every `Detekt` task in a project, so the
androidMain, jvmMain, `iosArm64`, `iosSimulatorArm64` and test source sets are all analysed —
previously only the default `detekt` task ran, and the shared config was never loaded at all.

Detekt reaches every module, including `androidApp` and `codegen:viewmodel-inject-processor`.
`build-logic` is a separate included build, so it applies detekt and spotless itself and the root
`check` depends on `:convention:check` explicitly.

The Compose rules come from `io.nlopez.compose.rules:detekt` via the `detekt-compose-rules` catalog
alias — the maintained successor to the abandoned `com.twitter.compose.rules` artifact that was
previously hardcoded in the convention plugin. Its ruleset id is `Compose`.

There is no detekt baseline file. `config.validation` is on, so an unknown ruleset or rule key fails
the build rather than being silently ignored.

### Formatting

Spotless runs ktlint. Shared style — indentation, line length, trailing commas and the
ktlint-specific keys — lives in `.editorconfig`, which ktlint reads directly, so there is no
override map in the Gradle build that can drift away from what the IDE applies.

## Automation

### GitHub Actions

CI lives in `.github/workflows/check.yml` and runs two jobs:

| Job | Runner | Runs |
|-----|--------|------|
| `check` | `ubuntu-latest` | `./gradlew check`, then `:androidApp:assembleDebug` |
| `ios` | `macos-latest` | `:composeApp:linkDebugFrameworkIosArm64` |

Splitting them keeps the expensive macOS runner scoped to the one task that needs it. Both jobs
have `timeout-minutes`, the workflow declares `permissions: contents: read`, and a `concurrency`
group keyed on workflow and ref cancels superseded runs. On failure each job uploads its detekt,
lint, test and Kover reports as artifacts so a red build can be diagnosed without re-running it.

### Dependabot

Dependabot configuration lives in `.github/dependabot.yml` and tracks Gradle dependencies and
GitHub Actions versions. Updates are grouped so related upgrades arrive as one reviewable PR:

- `kotlin` — Kotlin and KSP, which are versioned against each other and must move together
- `androidx` — the AndroidX artifacts
- `all-minor-patch` — everything else, by update type

Both ecosystems set `open-pull-requests-limit` so the queue stays reviewable.

Keep automation generic and reusable. Do not add org-specific secrets, deploy steps, or proprietary checks.

## Coverage

Kover is applied to every subproject from the root build and aggregated there, so `./gradlew check`
produces one merged report:

| Report | Path |
|--------|------|
| XML | `build/reports/kover/report.xml` |
| HTML | `build/reports/kover/html/index.html` |

Both are wired to `check` via `onCheck`, so no separate command is needed.

**What it actually covers.** Kover instruments JVM bytecode. In this repository that means the
`jvm` target's tests and the Android unit tests — the iOS targets produce no coverage data and do
not appear in the report. A module whose logic is only exercised from a native test will read as
uncovered even though it is tested. Read the number as "JVM-side coverage", not project coverage.

No coverage threshold is configured. The report exists to be looked at, not to fail the build on an
arbitrary percentage.

`build-logic` is a separate included build and is deliberately outside the aggregation.
