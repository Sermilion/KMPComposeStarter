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

Each platform merges its own component (`AndroidApplicationComponent` in `androidApp`,
`IosApplicationComponent` and `JvmApplicationComponent` in `composeApp`) and exposes it through
the shared `AppComponent` interface. Every host creates its graph once per process, outside
composition, and hands it to `StarterRoot(component)`.

## `UserScope`

`UserScope` represents a logged-in session.

Objects tied to a specific authenticated user should live here, including user-specific data
access like `StarterUserDao`, the `TokenStore`, and the `UserSessionScope` coroutine scope.

`StarterUserComponentManager` owns the session: a `createComponent` for a different user replaces
the previous session, and teardown cancels `UserSessionScope` then closes every
`UserScopedCloseable` multibinding before the component reference is cleared. Anything holding a
resource that must not outlive the session should be multibound as a `UserScopedCloseable`.

## `ScreenScope`

`ScreenScope` is the shortest-lived scope and is intended for route/screen-level objects.

Use it for:

- screen ViewModels
- screen-specific coordinators
- small state holders tied to one destination

The shared `ScreenComponent` lives in `core:domain`. `injectViewModel` creates it once per nav
entry, in a `ScreenComponentHolder` stored in that entry's `ViewModelStore`, so every
`@SingleIn(ScreenScope::class)` binding resolved on one screen shares an instance and the
component dies with the entry.

Navigation 3 keys those per-entry stores by content key, so a store keyed `HomeRoute` would
otherwise outlive a logout and hand the next user the previous session's screen component and
ViewModels. `rememberNavEntryViewModelStoreOwner` (`core:ui`) is the owner the nav-entry decorator
is built with: it keeps entry state across configuration changes and drops every entry store when
the session ends. Signing in deliberately keeps the pre-auth stores — nothing user-scoped lives in
them, and clearing them would cancel the ViewModel coroutine completing the sign-in.

## Composition Wiring

At runtime the platform entry points install shared dependencies into composition via locals such as:

- `LocalViewModelFactory` (pre-auth screens)
- `LocalUserComponentManager`
- `LocalScreenComponentFactory`

This keeps platform hosts thin while still letting shared UI resolve screen-scoped dependencies.

## Guidance for New Code

- Default to the narrowest scope that matches the lifetime you need.
- Do not promote feature state to `AppScope` just to make wiring easier.
- Prefer injecting stable managers or IDs over passing around heavy graph objects manually.
- Register every ViewModel with `@ContributesViewModel(scope)`; the KSP processor generates its
  `ViewModelEntry` multibinding. Do not hand-write a registration module or add a second factory.
