# Dependency Injection and Scoping

This repository uses `kotlin-inject` with Anvil-style merging and generated ViewModel wiring.

The goal is to keep dependency lifetimes explicit, avoid reflection-heavy DI, and make screen-level wiring work consistently across Android, iOS, and JVM.

## Scope Hierarchy

```text
AppScope
  -> UserScope
    -> ScreenScope
```

## Component Ownership

| Layer | Current owner | Lifetime |
|-------|---------------|----------|
| App component | `AndroidApplicationComponent`, `IosApplicationComponent`, `JvmApplicationComponent` | Process/app lifetime |
| User component | `StarterUserComponentManager` creating `UserComponent` | Logged-in user session |
| Screen component | `ProvideScreenComponentFactory` + `ScreenComponent` | Current authenticated destination |

The app component exists on every supported platform. It provides shared graph roots like the app-level ViewModel provider, user-component manager, repositories, and Room infrastructure.

## `AppScope`

`AppScope` is for process-wide singletons.

Typical examples in this repository include:

- `StarterViewModelFactory`
- `StarterUserComponentManager`
- `StarterRoomDatabaseProvider`
- `PlatformRoomDatabaseBuilderFactory`
- app-wide repositories and remote/local auth data sources
- platform application components such as `AndroidApplicationComponent`

Use `AppScope` for services that should survive navigation changes and authenticated-session resets.

Do not move feature state here just because it is convenient to access.

## `UserScope`

`UserScope` represents a logged-in user session.

Use it for objects that should be recreated when the active user changes, including:

- `UserComponent`
- user-specific DAOs such as `StarterUserDao`
- user-bound caches or services backed by the active `UserDatabase`

The user component is created from the app component and should be destroyed on logout so user-bound state does not leak across sessions.

## `ScreenScope`

`ScreenScope` is for authenticated route-level objects.

Use it for:

- screen ViewModels
- route-specific coordinators
- small state holders tied to one authenticated destination
- `DIViewModelFactory`
- `ScreenComponent`

Current examples include `HomeViewModel`, `ProfileViewModel`, and `SettingsViewModel`, all scoped with `@SingleIn(ScreenScope::class)`.

## Composition Wiring

Platform entry points install shared graph access into composition through:

- `LocalViewModelProvider`
- `LocalUserComponentManager`
- `LocalScreenComponentFactory`

Current hosts that do this include:

- `androidApp/.../MainActivity.kt`
- `composeApp/src/iosMain/.../MainViewController.kt`
- `composeApp/src/jvmMain/.../Main.kt`

`ProvideScreenComponentFactory` bridges authenticated UI into a lazily created `ScreenComponent`.

This keeps hosts thin while allowing shared Compose UI to resolve dependencies consistently on every platform.

## ViewModel Injection Patterns

### Onboarding and logged-out flows

Use:

```kotlin
val viewModel = injectViewModel<MyViewModel>(scope = ViewModelScope.Onboarding)
```

This resolves ViewModels through the app-level provider instead of the authenticated screen component.

Current onboarding-scoped routes include:

- `LoginViewModel`
- `RegisterViewModel`

These ViewModels are `@Inject`-constructible and are provided through `StarterViewModelFactory`.

### Authenticated feature flows

Use:

```kotlin
val viewModel = injectViewModel<MyViewModel>()
```

The default `Feature` scope resolves through the current `ScreenComponent`.

If `LocalScreenComponentFactory` is unavailable, `injectViewModel()` fails loudly with an authentication-related error instead of silently falling back to the wrong scope.

That is intentional: authenticated screens should not pretend they can work without a valid user session.

### Assisted arguments and `SavedStateHandle`

For runtime route arguments, pass them through `assisted`:

```kotlin
val viewModel = injectViewModel<MyViewModel>(
  assisted = MyViewModel.Arguments(id = itemId),
)
```

`DIViewModelFactory` also attempts to create and merge `SavedStateHandle` automatically via `CreationExtras`.

Important rules:

- `SavedStateHandle` does not need to be passed manually from the composable.
- the generated ViewModel factory path handles `savedStateHandle` under the hood
- if you add a new injected ViewModel, follow the existing generated wiring pattern rather than introducing a separate factory style

## Choosing the Right Scope

Use the narrowest scope that matches the actual lifetime:

- **App-wide forever**: `AppScope`
- **One authenticated user session**: `UserScope`
- **One authenticated route/screen**: `ScreenScope`

Prefer stable IDs, providers, or managers over manually passing heavy graph objects around.

## Troubleshooting

### `ScreenComponentFactory not provided`

This usually means authenticated UI was rendered without a valid user session or outside `ProvideScreenComponentFactory`.

Check:

- whether the route should really be authenticated
- whether `LocalUserComponentManager` and `ProvideScreenComponentFactory` are installed at the host
- whether you accidentally used feature scope for onboarding UI

### `ViewModel not found`

This usually means the ViewModel is not part of the generated DI entry set.

Check:

- whether the ViewModel has `@Inject`
- whether it is placed in the expected source set/module
- whether the intended scope matches the way it is being resolved

## Guidance for New Code

- Default to the narrowest scope that matches the lifetime you need.
- Do not promote feature state to `AppScope` just to make wiring easier.
- Prefer reusing `injectViewModel()` and the existing generated ViewModel-entry flow.
- Keep app-shell, user-session, and route-level lifetimes clearly separated.
