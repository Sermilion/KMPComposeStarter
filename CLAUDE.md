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
- `core:domain`: domain contracts, screen components, shared business rules
- `core:ui`: shared UI wiring and screen-component helpers
- `core:testing`: shared test helpers
- `feature:*`: app-facing features and screen-scoped presentation logic
- `codegen:viewmodel-inject-processor`: generated ViewModel wiring support
- `build-logic/convention`: reusable Gradle convention plugins

### Architectural Expectations

- Keep module boundaries clean: `data` implements contracts, `domain` stays framework-light, and `feature` code should not bypass shared abstractions without a strong reason.
- Keep the starter generic and reusable. Avoid product-specific naming, secrets, endpoints, or release workflows.
- Prefer shared configuration in `build-logic` and `gradle/libs.versions.toml` over duplicated per-module Gradle setup.

## Dependency and Build Guidance

- Manage plugin and library versions through `gradle/libs.versions.toml`.
- Build the repository with JDK 17, while keeping Android and JVM bytecode targets on Java 11 unless the template intentionally raises its runtime baseline.
- Prefer stable repositories only. Do not add `mavenLocal()`, JitPack, or preview feeds unless a dependency truly requires one and the reason is documented.
- Keep Android app bootstrap in `androidApp` and shared Kotlin Multiplatform code in modules that use the Android KMP library plugin.
- If build logic changes, re-run `./gradlew check` from the repository root.

## Testing and Quality Standards

- `./gradlew check` is the default release gate for this starter.
- CI should also validate at least one iOS framework link task.
- Do not add suppressions to hide real build, lint, or test failures unless the template intentionally documents them.
- `core:data` intentionally disables `RestrictedApi` lint while Room 3 `alpha01` reports false positives on generated KSP code and `RoomDatabase` usage in KMP lint tasks. Revisit that workaround on future Room upgrades.
- Prefer Kotest assertions and MockK annotations over ad-hoc mocking setup when practical.
- When adding repository APIs, prefer both single-item and bulk write operations instead of forcing callers into item-by-item loops.
- Treat DataStore file renames as migrations. Renaming a file without a migration path can strand existing user data.
- Commit Room schema exports when Room schemas change.

## Documentation Workflow Expectations

- Read `docs/ARCHITECTURE.md` before making structural changes.
- Read the focused docs under `docs/architecture/` before changing architecture, navigation, persistence, or build behavior.
- When changing contributor workflows, update `README.md`, the relevant docs under `docs/`, and this file together.
- Explain template-wide decisions, especially dependency upgrades, repository-policy changes, and DI or navigation changes.

## Compose and Navigation Guidance

- Keep ViewModel-backed entry points thin and hoist render state into previewable content composables where practical.
- Prefer passing stable IDs rather than full mutable objects through navigation and UI events when a deeper layer remains the source of truth.
- Keep auth-only and authenticated flows clearly separated.
- Preserve explicit back stack ownership instead of hiding navigation rules in opaque helpers.
- Prefer surgical changes over broad churn, but complete the root fix when you touch build logic or starter docs.
