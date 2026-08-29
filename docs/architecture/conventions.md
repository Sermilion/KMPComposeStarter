# Conventions

## Keep the Starter Generic

This repository is a template, not a product codebase.

Avoid adding:

- company-specific secrets or endpoints
- organization-specific release flows
- product naming that future consumers must undo
- tightly coupled integrations that belong in a real app, not a starter

## Source-of-Truth Rules

- Keep domain contracts and long-lived sources of truth in shared layers.
- Do not let UI state become the only owner of data that a repository or database should own.
- When navigation or UI events refer to persistent entities, prefer stable IDs over copying full objects through multiple layers.

## Repository Design Guidance

- `core:data` should implement shared contracts instead of leaking transport or persistence details upward.
- Prefer exposing both single-item and bulk write operations when callers are likely to save collections.
- Keep mapping logic close to the data layer instead of re-implementing it in every feature.

## Compose Guidance

Adapted from the kinds of practices that scale well in larger Compose apps:

- Keep ViewModel-backed entry points thin.
- Hoist display state into reusable content composables where practical.
- Make previewable UI easy to render with sample state and minimal wiring.
- Keep side effects and navigation triggers explicit instead of burying them in deeply nested UI code.
- Every public screen composable takes a `modifier: Modifier = Modifier` and applies it at its own
  root — `modifier.fillMaxSize()`, never `Modifier.fillMaxSize().then(modifier)`, which lets the
  screen's own sizing win over the caller's.
- Collect a `StateFlow` in composeApp with `collectAsStateWithLifecycle()`, not `collectAsState()`.
- Stateless content composables and the design-system components carry light and dark `@Preview`
  functions. They use `org.jetbrains.compose.ui.tooling.preview.Preview` so they live in
  `commonMain`, and `check` compiles them — a broken preview fails the same gate as a broken screen.

### One-Off Effects

There is exactly one one-off-effect idiom in this app: `Effect<T>` in
`core:common` (`com.sermilion.kmpcomposestarter.common.coroutines.Effect`). It wraps a
`Channel(Channel.BUFFERED)` and exposes a `Flow<T>`.

Use it for things that happen once — navigate, show a snackbar, dismiss a sheet. State that a
screen re-reads on every recomposition belongs in a `StateFlow` instead.

A `MutableSharedFlow()` is not an acceptable substitute, and none remain in the tree. Its default
replay is zero, so anything emitted while no collector is attached is dropped — and a screen has no
collector between leaving the composition and re-entering it. A navigation request made in that
window was silently lost and the user simply stayed where they were. The channel buffers instead,
and hands each value to exactly one collector.

The shape every feature ViewModel uses:

```kotlin
private val _effects = Effect<MyContract.Event>()
val effects: Flow<MyContract.Event> = _effects.flow
```

A ViewModel with no one-off effects — `SettingsViewModel` — declares no `Effect` at all. Do not add
one for symmetry.

### Theming

`StarterTheme(darkTheme: Boolean = isSystemInDarkTheme(), dynamicColor: Boolean = false)` is the one
theme every host renders through.

- Both schemes are complete: every Material 3 role the app renders is assigned explicitly. An unset
  role falls back to the Material baseline purple, which is how non-brand colour reaches a screen
  nobody previewed.
- Both are tonal ramps of one brand seed, `Green600` (`#92B215`). A fork rebrands by regenerating
  from a new seed, not by hand-editing forty roles. The light `primary` is a darker tone of that
  seed, because the raw seed carries white text at only 2.2:1.
- `dynamicColor` defaults to `false` so a fork sees the template's own scheme first. On Android 12+
  it opts into the wallpaper palette; every other target ignores it.
- `secondaryContainer` and the `surfaceContainer*` ramp are deliberately distinct from `surface` in
  both schemes. Flattening them removes Material's tonal elevation and makes the selected
  bottom-bar tab indistinguishable from the bar behind it.
- Measured WCAG contrast ratios (AA needs 4.5:1 for body text):

  | Pairing | Light | Dark |
  | --- | --- | --- |
  | `primary` / `onPrimary` | 6.41:1 | 7.72:1 |
  | `onSurface` / `surface` | 16.36:1 | 14.42:1 |

- On Android the window theme is a DayNight pair (`values/themes.xml` plus
  `values-night/themes.xml`), so the window background painted before the first Compose frame
  follows the system setting. `MainActivity` calls `enableEdgeToEdge()`, which owns system-bar
  transparency and icon contrast; the theme deliberately does not set those attributes.

## Navigation Guidance

- Keep route definitions typed and serializable.
- Keep auth-only and authenticated routes clearly separated.
- Preserve explicit back stack ownership rather than hiding it in custom magic.

## Persistence Guidance

- Use Room for relational local data.
- Use DataStore for small preference-style settings.
- Treat DataStore file renames as migrations, not cosmetic refactors.
- Commit Room schema exports when the schema changes.
- Key user-scoped storage by the signed-in user's ID, and release it in a `UserScopedCloseable`.
- Check the result of a deletion. Reporting success while user data is still on disk is worse than
  reporting failure.

## Networking Guidance

- There is one process-wide `HttpClient`, configured in `core:data`'s `HttpClientModule`.
- It reads the bearer token through `UserComponentManager` on every request rather than capturing a
  `TokenStore`, so a signed-out session cannot keep authorizing requests.
- The base URL comes from the `starter.api.baseUrl` Gradle property, defaulting to the deliberately
  fake `https://api.example.com/`. Never commit a real endpoint or secret to this template.
- Keep credentials out of logs: the `Logging` plugin sanitizes the `Authorization` header, and any
  new sensitive header must be added there too.
- Repositories talk to data-source interfaces, never to `HttpClient` directly. The starter ships one
  `@ContributesBinding` implementation, `MockAuthRemoteDataSource`; a fork swaps that single binding.
- Because that binding is a fake, no shipped code path issues a real request. The client is still
  built, configured and tested — `StarterHttpClientTest` pins bearer-token attachment and base-URL
  resolution — but nothing injects it until a fork replaces the mock. Treat it as wired-and-tested
  scaffolding for that swap, not as a feature path this template exercises.

## Documentation Workflow

When changing starter-wide behavior, update the relevant docs in the same change:

- `README.md` for user-facing setup or feature changes
- `docs/architecture/*.md` for architecture or contributor guidance
- `AGENTS.md` and `CLAUDE.md` for agent-facing workflow expectations
- `docs/window-insets.md` when scaffold, edge-to-edge, or inset behavior changes

Documentation is part of the template contract, not optional follow-up work.
