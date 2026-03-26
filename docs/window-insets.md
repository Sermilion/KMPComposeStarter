# Window Insets and Edge-to-Edge

This repository draws edge-to-edge on Android.

`androidApp/src/main/kotlin/news/readian/notoesapp/MainActivity.kt` calls `enableEdgeToEdge()`, so screens should assume system bars may overlap content unless they handle insets correctly.

## Critical Rules

1. **Do not add system-bar padding to the `Scaffold` modifier.**
2. **Prefer `contentWindowInsets` on `Scaffold` so ownership stays explicit.**
3. **Be clear about which layer owns bottom-bar spacing: the root app shell or the screen.**
4. **Preview and test IME, gesture navigation, and 3-button navigation states when changing layout code.**

## Current App Layout

The Android host currently looks like:

```text
MainActivity (enableEdgeToEdge)
└── StarterApp
    └── Scaffold
        ├── StarterNavDisplay
        └── StarterBottomBar (authenticated tab roots only)
```

Important consequences:

- `StarterApp` owns the authenticated bottom bar.
- `StarterBottomBar` uses Material 3 `NavigationBar`, which already applies navigation-bar insets for itself.
- Auth and onboarding screens such as `WelcomeScreen`, `LoginScreen`, and `RegisterScreen` use `contentWindowInsets = WindowInsets.statusBars`.

## Recommended Patterns

### Root app shell with bottom navigation

When the root app shell owns the bottom navigation, let the root `Scaffold` manage its normal padding and pass `innerPadding` into the content container.

That matches the current `StarterApp` implementation.

Do **not** add extra `navigationBarsPadding()` to `StarterBottomBar` unless you intentionally disable the `NavigationBar` default insets. Otherwise you will double-pad the bar.

### Auth and onboarding screens

For full-screen auth or onboarding surfaces with a top app bar or vertically scrolling content, prefer:

```kotlin
Scaffold(
  topBar = { /* ... */ },
  contentWindowInsets = WindowInsets.statusBars,
) { innerPadding ->
  Box(
    modifier = Modifier
      .imePadding()
      .fillMaxSize()
      .padding(innerPadding),
  ) {
    // Screen content
  }
}
```

This is the pattern currently used by:

- `feature/onboarding/.../WelcomeScreen.kt`
- `feature/auth/.../LoginScreen.kt`
- `feature/auth/.../RegisterScreen.kt`

### Long scrolling lists

If a new screen contains a `LazyColumn` or `LazyGrid` where the last item must remain fully visible above the system navigation bar, add bottom content padding to the list content, not to the entire scaffold:

```kotlin
LazyColumn(
  modifier = Modifier.fillMaxSize(),
  contentPadding = WindowInsets.navigationBars.asPaddingValues(),
) {
  // items
}
```

Use this when the screen itself owns bottom-edge spacing. If the root app shell already owns a bottom bar and related layout padding, be deliberate and avoid adding padding twice.

### Screens with the keyboard

For forms, combine `innerPadding` with `imePadding()` on the outer container so fields and actions can move above the keyboard without fighting scaffold insets.

That is the current pattern in `LoginScreen` and `RegisterScreen`.

### Full-screen immersive content

If a future screen intentionally needs to draw behind both status and navigation bars, set zero insets and handle overlays manually:

```kotlin
Scaffold(
  contentWindowInsets = WindowInsets(0, 0, 0, 0),
) { /* ... */ }
```

Use this sparingly and document why, because most screens should stay on `statusBars` or the scaffold default.

## Avoid These Mistakes

- `Modifier.systemBarsPadding()` on the scaffold root
- `Modifier.windowInsetsPadding(...)` on the scaffold root
- extra bottom-bar padding on Material 3 `NavigationBar` without first disabling its default insets
- forgetting list `contentPadding` on long scrollable surfaces where the last item needs to clear the navigation bar

## Practical Review Checklist

When reviewing or editing a screen:

- Does the `Scaffold` declare the right `contentWindowInsets`?
- Is `innerPadding` applied exactly once at the right container level?
- Does the screen rely on a root-owned bottom bar, or does it own its own bottom spacing?
- Does any scrollable content need `navigationBars.asPaddingValues()`?
- Does the screen still behave correctly with the IME open?

## References

- [Android edge-to-edge guidance](https://developer.android.com/develop/ui/compose/layouts/inset-padding)
- [Compose `WindowInsets` reference](https://developer.android.com/reference/kotlin/androidx/compose/foundation/layout/WindowInsets)
