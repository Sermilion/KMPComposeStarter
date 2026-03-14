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
composeApp -> shared application shell, platform entry points, root UI wiring
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
- `composeApp` owns app-shell concerns and platform entry points.

### Compile-time dependency injection

The project uses `kotlin-inject` plus Anvil-style merging for predictable DI graphs and faster startup than reflection-based DI.

### Explicit navigation state

Navigation is not hidden behind an opaque framework wrapper. `StarterNavigationState` owns the stacks, and `StarterNavigator` applies the rules.

That keeps auth gating, top-level tab resets, and back behavior visible in code.

### Shared persistence with platform builders

Room 3 is the shared persistence layer.

The starter keeps one onboarding database plus one user-specific database per active user, with platform-specific builders handling file locations and driver details.

## Current Tooling Constraint

The repository already targets AGP 9 and Gradle 9.1.0, but Kotlin Multiplatform shared modules still use the temporary Android compatibility bridge.

That is why `gradle.properties` currently keeps:

- `android.builtInKotlin=false`
- `android.newDsl=false`

Treat that as a temporary compatibility measure, not a long-term architectural destination.
