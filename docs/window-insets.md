# Window Insets

This document describes the window-inset behavior the starter actually ships today. It is
deliberately narrow: everything below is visible in `composeApp/src/commonMain/.../App.kt` and
`androidApp/src/main/kotlin/.../MainActivity.kt`. Nothing else about insets is implemented.

## Shared app shell

`StarterApp` in `App.kt` wraps the whole app in a Material 3 `Scaffold`:

- The `Scaffold` uses its **default `contentWindowInsets`**. The starter does not override,
  consume, or exclude any inset type.
- The content lambda receives `innerPadding` and applies it via `Modifier.padding(innerPadding)`
  on the `Box` that hosts `StarterNavDisplay`.

That means every destination rendered by `StarterNavDisplay` is inset by whatever the `Scaffold`
default resolves to on the current platform, plus the bottom bar when one is shown.

## Android

`MainActivity.onCreate` calls `enableEdgeToEdge()` before `setContent`. That is the only
Android-specific inset call in the repository.

## What is not implemented

The starter has no custom `WindowInsets` values, no per-screen inset overrides, no
`consumeWindowInsets` calls, no IME-specific handling, and no iOS or desktop inset code beyond
the shared `Scaffold` behavior described above. Screens that need different inset behavior must
add it themselves.
