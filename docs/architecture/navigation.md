# Navigation

## Navigation Model

Navigation is state-driven and intentionally explicit.

`StarterNavigationState` owns:

- the unauthenticated auth-flow back stack
- one back stack per authenticated top-level tab
- the current tab
- whether the user is authenticated

`StarterNavigator` is the small coordinator that mutates that state. `StarterNavDisplay` renders it.

## Route Types

The shared route model distinguishes between:

- `AuthFlowRoute`
- `TopLevelRoute`

That split keeps auth-only destinations separate from the authenticated tab destinations.

`TopLevelTab` is the single source of tab knowledge: each entry carries its start route, icon, and
label. The bottom bar reads those fields rather than re-deriving them in a `when`, and `Route.isTabRoot`
is defined from `TopLevelTab.entries`. Adding a tab means adding one enum constant.

## Behavioral Rules

### Logged-out flow

When the user is not authenticated, only auth-flow routes may be pushed.

### Logged-in flow

When the user is authenticated, top-level tabs each own their own back stack.

Switching tabs preserves each tab's stack.

Re-selecting the current tab resets that tab back to its start destination.

### Rejected navigation

`StarterNavigator.navigate` returns `Boolean` and refuses three things outright:

- a **tab root** as a pushed destination. A tab root pushed onto another tab's stack would carry the
  same content key as that tab's own root entry, so the two entries would share — and then lose —
  each other's saved state and ViewModel store. Move between tabs with `navigateToTopLevel`.
- an auth route while signed in.
- a top-level route while signed out.

A rejection logs at ERROR through an injectable `onRejectedNavigation` handler and leaves the state
object untouched (same instance, same stacks). The handler is a constructor parameter so tests can
assert on the rejection instead of on a log line.

Rejections are loud but not fatal: the template does not carry a cross-platform debug/release flag,
so there is nothing to key a `check(...)` off that would not also crash a release build. If your fork
adds such a flag, escalating this handler to an assertion in debug builds is the intended upgrade.

### Back behavior

Back navigation pops within the current auth stack or current tab stack.

If the active stack has only one element left, `goBack` returns `false` so the host can decide what to
do next.

`NavDisplay` enables its own back handling only while the current back stack has a previous entry, so
the "back at a non-HOME tab root returns to HOME" policy cannot live inside `goBack`. It lives in
`App.kt` as a host-level `NavigationBackHandler`, enabled by
`StarterNavigationState.backAtTabRootShouldReturnHome`. On Android, predictive back needs
`android:enableOnBackInvokedCallback="true"`, which `androidApp`'s manifest declares.

## Tab Retention

`StarterNavDisplay` calls `rememberDecoratedNavEntries` once per back stack — one call per tab plus
one for the auth stack — and passes the resulting entries to `NavDisplay(entries = ...)`. That is what
navigation3-runtime prescribes for multiple back stacks: entries for an inactive tab stay alive, so
switching away and back does not recreate that tab's ViewModels or drop its saved state.

The decorators, shared across every stack, are applied in this order:

1. `rememberSaveableStateHolderNavEntryDecorator` — `rememberSaveable` state inside an entry.
2. `rememberSavedStateNavEntryDecorator` — a per-entry `SavedStateRegistryOwner` (see below).
3. `rememberViewModelStoreNavEntryDecorator` — a `ViewModelStore` per entry.

The ViewModel-store decorator requires a `LocalSavedStateRegistryOwner` whose lifecycle is still
`INITIALIZED`, and points at a `SavedStateNavEntryDecorator` that no released Navigation 3 artifact
ships. `NavEntrySavedStateDecorator.kt` fills that gap with a small owner whose lifecycle is pinned at
`INITIALIZED` and whose registry is saved and restored through a `Saver`. It is marked as a shortcut
in the source: delete it once the library ships its own.

The navigator must therefore keep each tab's `SnapshotStateList` **instance** across a tab switch,
not merely its contents — the display keys a tab's entries on the list it was handed.
`StarterNavigatorTest` asserts that identity. ViewModel-instance retention itself is *argued* from
the `rememberDecoratedNavEntries` contract described above, not asserted by a Compose UI test; the
template has no Compose UI test harness, and adding a JUnit4-based one to a Kotest/JUnit5 project was
judged disproportionate. Treat retention as a documented expectation, not as covered behavior.

## Process Death and Restore

`StarterAppState` holds the navigation state in `rememberSaveable(stateSaver = StarterNavigationStateSaver)`.
The saver encodes the whole state — auth flag, auth stack, every tab stack, current tab — as JSON
through `starterSerializersModule`, so a rotation or a process death restores the user to the exact
screen they were on rather than to a tab root. A restored stack that decodes empty falls back to
`LoginRoute` or the tab's start route, and a payload the current build cannot decode at all — a
route whose shape changed in an app update — restores as null so `rememberSaveable` starts fresh
instead of crashing on every cold start.

Every route the entry provider registers must also be registered in `starterSerializersModule`, in
each polymorphic block it belongs to. A route missing there throws on the first restore, in
production, on a screen nobody tested. `StarterRouteSerializationTest` reads the registered routes out
of `StarterEntryProvider.kt` rather than listing them a second time, asserts each is registered under
every polymorphic base it implements, and round-trips a sample instance of each.

Screen-level state that must outlive process death belongs in a `SavedStateHandle`, which the
ViewModel-store decorator makes available per entry.

## Adding New Routes

- Define a typed `@Serializable` route next to the screen it opens, in that screen's feature module.
- Register it in `starterSerializersModule`, in every polymorphic block the route's bases cover, and
  add a sample instance to `routeSamples` in `StarterRouteSerializationTest`.
- Add a destination entry in `createStarterEntryProvider`.
- Pass stable identifiers rather than full mutable domain objects whenever a deeper layer remains the
  source of truth.

### DetailRoute, the parameterised exemplar

`feature:home` owns `DetailRoute(id)`, `DetailViewModel`, and `DetailScreen` as the worked example of
a pushed, argument-carrying destination. It lives in `feature:home` and not in `composeApp` because a
route belongs with the screen it opens; `composeApp` only wires it.

`HomeViewModel` emits `NavigateToDetail(id)` — an id, not a domain object — and the entry provider
turns that into `navigator.navigate(DetailRoute(event.id))`. The entry passes the id to the ViewModel
as an assisted argument:

```kotlin
entry<DetailRoute> { route ->
  val viewModel = injectViewModel<DetailViewModel>(assisted = mapOf("id" to route.id))
  ...
}
```

The assisted key must match the ViewModel's constructor parameter name exactly — that is the key the
generated `*_Entry` reads. `DetailViewModel` also takes an assisted `SavedStateHandle` and keeps its
note field there, so the note survives process death. `DetailViewModelTest` builds the ViewModel
through those same keys, because a key mismatch renders an empty screen rather than failing.

## Auth Transitions

There is exactly one mechanism that moves the app between the auth flow and the signed-in shell: the
session flow `StarterAppState` collects, which calls `StarterNavigator.onLoginStateChanged`.
ViewModels do **not** emit login/logout success events; a second, screen-driven signal would race the
session flow and the screens would disagree about which one won.

Signing out rebuilds every tab stack and resets the current tab to `HOME`, so the next user cannot
sign in onto the previous user's tab or see their pushed route.

## Why this structure exists

This pattern keeps navigation rules easy to debug and easy to document.

It also matches the kind of explicit back stack ownership that scales better once auth state, tabs,
and deep links become more complex.
