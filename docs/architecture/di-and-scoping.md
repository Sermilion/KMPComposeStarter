# Dependency Injection and Scoping

## Scope Hierarchy

The starter uses three main scopes:

```text
AppScope
  -> UserScope
    -> ScreenScope
```

## `AppScope`

`AppScope` contains process-wide singletons.

Examples include:

- dispatcher providers
- app coroutine scope
- session management helpers
- Room database provider infrastructure
- remote data sources and app-level repositories

Platform application components are merged in `composeApp` for Android, iOS, and JVM.

## `UserScope`

`UserScope` represents a logged-in session.

Objects tied to a specific authenticated user should live here, including user-specific data access like `StarterUserDao`.

The user component is created from the app scope and should be torn down on logout.

## `ScreenScope`

`ScreenScope` is the shortest-lived scope and is intended for route/screen-level objects.

Use it for:

- screen ViewModels
- screen-specific coordinators
- small state holders tied to one destination

The shared `ScreenComponent` lives in `core:domain`, and feature ViewModels use `@SingleIn(ScreenScope::class)`.

## Composition Wiring

At runtime the platform entry points install shared dependencies into composition via locals such as:

- `LocalViewModelProvider`
- `LocalUserComponentManager`
- `LocalScreenComponentFactory`

This keeps platform hosts thin while still letting shared UI resolve screen-scoped dependencies.

## Guidance for New Code

- Default to the narrowest scope that matches the lifetime you need.
- Do not promote feature state to `AppScope` just to make wiring easier.
- Prefer injecting stable managers or IDs over passing around heavy graph objects manually.
- If a new feature needs screen-scoped ViewModels, follow the existing generated ViewModel wiring pattern rather than introducing a second DI style.
