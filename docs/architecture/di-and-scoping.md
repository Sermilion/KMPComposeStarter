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
- the `UserPreferences` DataStore and the `AuthLocalDataSource` over it
- the single `HttpClient`, which reads the current bearer token through `UserComponentManager`
- `StarterSessionRestorer`, bound to the `core:domain` `SessionRestorer` contract, which rebuilds a
  stored session once per process at launch
- remote data sources and app-level repositories

Each platform merges its own component (`AndroidApplicationComponent` in `androidApp`,
`IosApplicationComponent` and `JvmApplicationComponent` in `composeApp`) and exposes it through
the shared `AppComponent` interface. Every host creates its graph once per process, outside
composition, and hands it to `StarterRoot(component)`.

## `UserScope`

`UserScope` represents a logged-in session.

Objects tied to a specific authenticated user should live here, including the session's
`UserDatabase`, `StarterUserDao`, `StarterUserRepository`, the `TokenStore`, and the
`UserSessionScope` coroutine scope.

`StarterUserComponentManager` owns the session: a `createComponent` for a different user replaces
the previous session, and teardown cancels `UserSessionScope` then closes every
`UserScopedCloseable` multibinding before the component reference is cleared. Anything holding a
resource that must not outlive the session should be multibound as a `UserScopedCloseable`.

`createComponent` and `destroyComponent` are `suspend`, and `UserScopedCloseable.close` with them:
releasing a session closes its Room database, which checkpoints the write-ahead log. Sign-out runs
from a ViewModel coroutine on the main dispatcher, so that work hops to IO rather than blocking the
frame. Teardown also runs outside the transition lock — publication has already happened, so nothing
can hand out a session that is being released.

### Where session orchestration lives

`StarterAuthRepository` does more than a repository name suggests: it calls the remote source,
creates the user component, writes the token and writes the user row, and on sign-out tears the
component down whether or not the network call succeeded. That is deliberate and it is the one
place allowed to do it.

The ordering is the reason. The session has to exist *before* the token and the user row are
written, or they land in the outgoing user's store and database; and sign-out has to destroy the
component in a `finally`, or a failed network call leaves an authenticated session alive.
Splitting those steps across a repository and a separate coordinator would put that ordering in two
places. `SecondLoginIntegrationTest` pins it.

Do not copy the shape for ordinary repositories. A repository that reads and writes one kind of
data should do only that; this one is the session boundary wearing a repository's interface.

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

- `LocalPreAuthViewModelFactory` (pre-auth screens)
- `LocalUserComponentManager`
- `LocalScreenComponentFactory`

This keeps platform hosts thin while still letting shared UI resolve screen-scoped dependencies.

## Guidance for New Code

- Default to the narrowest scope that matches the lifetime you need.
- Do not promote feature state to `AppScope` just to make wiring easier.
- Prefer injecting stable managers or IDs over passing around heavy graph objects manually.
- Register every ViewModel with `@ContributesViewModel(scope)`; the KSP processor generates its
  `ViewModelEntry` multibinding. Do not hand-write a registration module or add a second factory.
- A ViewModel reports one-off effects — navigation, snackbars — through the single `Effect<T>`
  idiom in `core:common`, never a `MutableSharedFlow`. See
  [One-Off Effects](conventions.md#one-off-effects) for why and for the shape to copy.
