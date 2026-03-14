# Modules

## Dependency Direction

Keep dependencies flowing inward toward shared abstractions.

- `feature` modules depend on shared `core` modules.
- `core:data` implements contracts that are consumed by other shared layers.
- `androidApp` depends on `composeApp` plus the Android-visible shared and feature modules it bootstraps.
- `composeApp` depends on the shared modules and hosts root composition.
- `build-logic` configures modules but should not leak app logic.

## Top-Level Areas

### `androidApp`

Owns:

- Android `MainActivity` and `StarterApplication`
- Android manifest and launcher resources
- Android-specific application component bootstrap

Keep Android-only entry points here instead of mixing them into shared multiplatform modules.

### `composeApp`

Owns:

- iOS `MainViewController`
- JVM desktop entry point
- Root app state and app-wide navigation wiring
- Shared ViewModel entry registration and app-shell composition

Keep feature-specific business rules out of this module unless they are truly app-shell concerns.

### `core:common`

Owns:

- common DI scopes and helpers
- shared coroutine and utility infrastructure
- route marker interfaces and common navigation contracts
- root-level ViewModel factory primitives

This is the lowest-level shared module and should stay broadly reusable.

### `core:data`

Owns:

- Room 3 databases, entities, DAOs, and schema exports
- repository implementations
- local and remote data sources
- per-user component and session management helpers

This module should implement data access details rather than define UI-facing behavior.

### `core:datastore`

Owns lightweight preference-style persistence.

Use it for settings and small structured values, not relational data that belongs in Room.

### `core:designsystem`

Owns:

- theme setup
- typography and color tokens
- shared Compose styling primitives

Keep design tokens centralized here instead of redefining them per feature.

### `core:domain`

Owns:

- shared domain contracts and use cases
- screen-component abstractions
- ViewModel integration primitives that should not live in app-shell code

Try to keep this layer framework-light and centered on business rules and shared contracts.

### `core:ui`

Owns shared UI wiring such as screen-component helpers and reusable app-level UI pieces that do not belong to one feature.

### `core:testing`

Owns shared testing helpers and reusable test support code.

### `feature:*`

Each feature module owns its own routes, screens, and screen-scoped ViewModels.

Current examples:

- `feature:auth`
- `feature:home`
- `feature:profile`
- `feature:settings`

Favor keeping feature-specific presentation logic inside the feature instead of leaking it into `composeApp`.

### `codegen:viewmodel-inject-processor`

This KSP processor generates ViewModel wiring based on the repository's DI scope conventions.

If DI scope names or ViewModel annotations change, inspect this module too.

## Package Placement Rules

- Put reusable contracts in `core` before reaching for a new feature-to-feature dependency.
- Put Android entry points in `androidApp` and other platform entry points in the appropriate `composeApp` source sets.
- Put relational persistence in `core:data`, not `core:datastore`.
- Put starter-wide build behavior in `build-logic` rather than copying Gradle setup across modules.
