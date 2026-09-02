# Agent Guidelines for Working with This Repository

This starter is meant to stay reusable and production-ready. Treat build logic, version catalog changes, documentation, and generated surfaces as template-level decisions rather than one-off app changes.

## Essential Build Commands

### Build and Verification

- Run the main quality gate: `./gradlew check`
- Build all modules: `./gradlew build`
- Format Kotlin and Gradle files: `./gradlew spotlessApply`
- Run static analysis only: `./gradlew detekt`

### Platform-Specific Tasks

- Build the Android debug app: `./gradlew :androidApp:assembleDebug`
- Build the iOS framework for device: `./gradlew :composeApp:linkDebugFrameworkIosArm64`
- Build the iOS framework for Apple Silicon simulator: `./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64`
- Run the desktop app: `./gradlew :composeApp:run`

## Project Architecture

### Module Structure

The repository is organized as a Kotlin Multiplatform application starter:

- `androidApp`: Android-only application module, manifest, launcher resources, and app bootstrap
- `composeApp`: shared app shell plus iOS/JVM entry points
- `core:common`: shared utilities, DI scopes, dispatchers, navigation contracts
- `core:data`: Room 3, repositories, local/remote data sources, user session infrastructure
- `core:datastore`: preference and settings persistence
- `core:designsystem`: shared Compose theme and design tokens
- `core:domain`: domain models, repository contracts, session contracts. Framework-light by rule: no `androidx.lifecycle`, Compose, Room, Ktor or DI component annotations
- `core:ui`: shared UI wiring and screen-component helpers
- `core:testing`: shared test helpers, consumed from the `jvmTest` source sets that use them
- `feature:*`: app-facing features and screen-scoped presentation logic
- `codegen:viewmodel-inject-processor`: generated ViewModel wiring support
- `build-logic/convention`: reusable Gradle convention plugins

### Architectural Expectations

- Keep module boundaries clean: `data` implements contracts, `domain` stays framework-light, and `feature` code should not bypass shared abstractions without a strong reason.
- Keep the starter generic and reusable. Avoid product-specific naming, secrets, endpoints, or release workflows.
- Prefer shared configuration in `build-logic` and `gradle/libs.versions.toml` over duplicated per-module Gradle setup. A module build file should declare plugins and dependencies only: the Android target, namespace, compiler arguments, test platform and KSP processors all come from the conventions.
- Every catalog alias must be referenced by something. Do not leave an unused alias behind "for later", and keep every version literal in `[versions]` with a reason comment on each pre-release pin.

## Dependency and Build Guidance

- Manage plugin and library versions through `gradle/libs.versions.toml`.
- Build the repository with JDK 17, while keeping Android and JVM bytecode targets on Java 11 unless the template intentionally raises its runtime baseline.
- Prefer stable repositories only. Do not add `mavenLocal()`, JitPack, or preview feeds unless a dependency truly requires one and the reason is documented.
- Keep Android app bootstrap in `androidApp` and shared Kotlin Multiplatform code in modules that use the Android KMP library plugin.
- If build logic changes, re-run `./gradlew check` from the repository root.

## Networking and Session Guidance

- There is one process-wide `HttpClient` (`core:data`'s `HttpClientModule`). It reads the bearer
  token through `UserComponentManager` on every request; do not capture a `TokenStore` in it.
- The base URL comes from the `starter.api.baseUrl` Gradle property and defaults to the fake
  `https://api.example.com/`. Never commit a real endpoint or secret to this template.
- Keep credentials out of logs. The `Logging` plugin sanitizes `Authorization`; add any new
  sensitive header there too.
- Repositories depend on data-source interfaces, not on `HttpClient`. The starter binds exactly one
  implementation, `MockAuthRemoteDataSource`; a fork replaces that single `@ContributesBinding`.
- The signed-in session persists as plain JSON via `core:datastore`. Before pointing the template at
  a real backend, move the token to the Keychain/Keystore/OS keyring.
- Anything keyed to the signed-in user belongs in `UserScope` and must be released through a
  `UserScopedCloseable`. Never let a stale session reference reach the next user's data.
- Desktop file locations honour `-Dstarter.dataDir`; keep tests pointed at a build directory rather
  than the developer's home directory.

## Testing and Quality Standards

- `./gradlew check` is the default release gate for this starter. It runs detekt against the shared
  `config/detekt/detekt.yml` over every source set of every module, ktlint, Android lint, the Kotest
  suites, and the merged Kover report at `build/reports/kover/`.
- CI runs two jobs: `check` plus `:androidApp:assembleDebug` on ubuntu, and the iOS framework link
  on macOS. Keep the iOS link task in CI so multiplatform breakages surface early.
- Do not add suppressions to hide real build, lint, or test failures unless the template intentionally documents them. In particular, do not add a detekt baseline file: fix the finding or scope the rule narrowly with a comment.
- The configuration cache is enabled. New build logic must be configuration-cache safe; do not relax
  `org.gradle.configuration-cache.problems` to get a green run.
- Kover instruments JVM bytecode only, so the coverage report covers the JVM and Android unit tests
  and not the iOS targets. Do not describe it as project-wide coverage.
- `core:data` intentionally disables `RestrictedApi` lint while Room 3 `alpha01` reports false positives on generated KSP code and `RoomDatabase` usage in KMP lint tasks. Keep it to the one commented disable plus the path-scoped `core/data/lint.xml` ignore, and revisit on future Room upgrades.
- Prefer Kotest assertions and MockK annotations over ad-hoc mocking setup when practical.
- When adding repository APIs, prefer both single-item and bulk write operations instead of forcing callers into item-by-item loops.
- Treat DataStore file renames as migrations. Renaming a file without a migration path can strand existing user data.
- Commit Room schema exports when Room schemas change.
- Check the result of a deletion. Reporting success while user data is still on disk is worse
  than reporting failure.

## Documentation Workflow Expectations

- Read `docs/ARCHITECTURE.md` before making structural changes.
- Read the focused docs under `docs/architecture/` before changing architecture, navigation, persistence, or build behavior.
- Read `docs/window-insets.md` before changing scaffold, edge-to-edge, or window-inset behavior.
- When changing contributor workflows, update `README.md`, the relevant docs under `docs/`, and this file together.
- Explain template-wide decisions, especially dependency upgrades, repository-policy changes, and DI or navigation changes.

## Compose and Navigation Guidance

- Keep ViewModel-backed entry points thin and hoist render state into previewable content composables where practical.
- Prefer passing stable IDs rather than full mutable objects through navigation and UI events when a deeper layer remains the source of truth.
- Keep auth-only and authenticated flows clearly separated.
- Preserve explicit back stack ownership instead of hiding navigation rules in opaque helpers.
- Prefer surgical changes over broad churn, but complete the root fix when you touch build logic or starter docs.
