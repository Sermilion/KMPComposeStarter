# Overview

## Purpose

`KMPComposeStarter` is a reusable Kotlin Multiplatform application starter for Android, iOS, and desktop JVM.

It aims to provide a clean, production-ready baseline without hard-coding product-specific workflows, services, or company conventions into the template.

## Supported Platforms

| Platform | Support | Notes |
|----------|---------|-------|
| Android | minSdk 26 / compileSdk 36 | Main mobile target |
| iOS | `arm64`, `simulatorArm64` | `iosX64` is intentionally not part of the current target matrix |
| Desktop JVM | Supported | Good for local runs and JVM-focused tests |

## High-Level Shape

```text
androidApp -> Android application module, manifest/resources, Android bootstrap
composeApp -> shared application shell, iOS/JVM entry points, root UI wiring
core:*     -> reusable infrastructure, data, domain, UI, and testing support
feature:*  -> app-facing features and screens
codegen:*  -> KSP support for generated ViewModel wiring
build-logic -> shared Gradle conventions
```

## Architectural Decisions

### Multi-module boundaries

The starter separates cross-cutting infrastructure from feature code.

- `core` modules hold reusable platform-agnostic building blocks.
- `feature` modules own feature UI and screen logic.
- `androidApp` owns Android-specific bootstrap and launcher concerns.
- `composeApp` owns shared app-shell concerns and non-Android platform entry points.

### Compile-time dependency injection

The project uses `kotlin-inject` plus Anvil-style merging for predictable DI graphs and faster startup than reflection-based DI.

### Explicit navigation state

Navigation is not hidden behind an opaque framework wrapper. `StarterNavigationState` owns the stacks, and `StarterNavigator` applies the rules.

That keeps auth gating, top-level tab resets, and back behavior visible in code.

### Shared persistence with platform builders

Room 3 is the shared persistence layer.

The starter opens one user-specific database per active user, with platform-specific builders handling file locations and deletion details.

`UserScope` owns that database: opening a session opens it, and signing out closes it through a
`UserScopedCloseable`. Session identity and the bearer token persist separately, in `core:datastore`,
so a returning user is restored at launch rather than sent back to the login screen.

## Current Build Shape

The repository targets AGP 9.1.0 and Gradle 9.4.0 with the Android app split into its own module.

- `androidApp` uses the Android application plugin.
- Shared modules use the Android KMP library plugin.
- `composeApp` remains the shared app shell for iOS and JVM plus root composition.
