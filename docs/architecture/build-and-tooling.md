# Build and Tooling

## Build System

Shared Gradle behavior lives in `build-logic/convention/`.

Important convention plugins include:

| Plugin | Purpose |
|--------|---------|
| `kmp.application` | Shared app-shell setup for `composeApp` |
| `kmp.library` | Shared KMP library module setup |
| `kmp.compose` | Shared Compose Multiplatform module setup |
| `kmp.kotlininject` | Common KSP DI wiring |
| `kmp.detekt` | Static analysis configuration |
| `kmp.lint` | Android lint configuration |
| `kmp.jacoco` | Coverage configuration |

Keep shared build behavior in these plugins rather than repeating Gradle setup per module.

## Version Management

All dependency and plugin versions are centralized in `gradle/libs.versions.toml`.

Current headline versions include:

- Kotlin `2.3.10`
- Compose Multiplatform `1.10.2`
- AGP `9.0.1`
- KSP `2.3.6`
- Room 3 `3.0.0-alpha01`
- Gradle `9.1.0`

Prefer updating the version catalog first, then adjusting convention logic only when compatibility actually requires it.

## Toolchains

- Build with JDK 17.
- Keep Android and JVM bytecode targets on Java 11 unless the starter deliberately raises its runtime baseline.
- Supported KSP targets currently include Android, `iosArm64`, `iosSimulatorArm64`, and JVM.

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

The module intentionally disables `RestrictedApi` to keep the repository gate focused on actionable issues. Revisit that workaround when upgrading Room.

## Quality Gates

Main commands:

```bash
./gradlew check
./gradlew detekt
./gradlew spotlessApply
./gradlew :androidApp:assembleDebug
./gradlew :composeApp:linkDebugFrameworkIosArm64
```

`./gradlew check` is the default repository gate.

CI should also keep verifying at least one iOS framework link task so multiplatform breakages are caught early.

## Automation

### GitHub Actions

CI lives in `.github/workflows/check.yml`.

It intentionally stays lightweight and template-safe:

- checkout
- JDK 17 setup
- Gradle setup
- repository `check`
- iOS framework link verification

### Dependabot

Dependabot configuration lives in `.github/dependabot.yml` and currently tracks:

- Gradle dependencies
- GitHub Actions versions

Keep automation generic and reusable. Do not add org-specific secrets, deploy steps, or proprietary checks.
