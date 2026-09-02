# KMP Compose Starter Architecture Docs

Start here for repository architecture, contributor workflow, and starter-specific conventions.

This documentation is intentionally split into focused files so contributors and agents can read the relevant parts without scanning a single large document.

## Recommended Reading Order

1. `docs/architecture/overview.md`
2. `docs/architecture/modules.md`
3. `docs/architecture/di-and-scoping.md`
4. `docs/architecture/navigation.md`
5. `docs/architecture/persistence.md`
6. `docs/architecture/build-and-tooling.md`
7. `docs/architecture/conventions.md`

Platform-behavior notes live alongside these files:

- `docs/window-insets.md`

## Contents

### Overview

- Repository purpose and supported platforms
- High-level module layout
- Main architectural decisions
- Current platform and tooling constraints

Read: `docs/architecture/overview.md`

### Modules

- Responsibilities for `composeApp`, `core`, `feature`, and `codegen`
- Dependency direction and module-boundary rules
- What belongs in each layer

Read: `docs/architecture/modules.md`

### Dependency Injection and Scoping

- `AppScope`, `UserScope`, and `ScreenScope`
- Platform application components
- User/session lifetime management
- Screen component expectations

Read: `docs/architecture/di-and-scoping.md`

### Navigation

- Typed route conventions
- Explicit back stack ownership
- Auth flow vs authenticated tabs
- Per-tab retention and process-death restore
- Navigation rules for new features

Read: `docs/architecture/navigation.md`

### Persistence

- Room 3 database layout
- Per-user database lifetime
- Platform-specific database builders
- Schema export and migration expectations
- Session and preference persistence through `core:datastore`

Read: `docs/architecture/persistence.md`

### Build and Tooling

- Convention plugins and version catalog usage
- KSP target setup
- CI and Dependabot
- AGP 9 compatibility bridge notes

Read: `docs/architecture/build-and-tooling.md`

### Conventions

- Reusable starter-safe contribution rules
- State hoisting, `Modifier`, lifecycle-aware collection and preview guidance
- The single `Channel`-backed `Effect` idiom for one-off events
- The `StarterTheme` contract: complete light/dark schemes, `dynamicColor`, measured contrast
- Repository and source-of-truth rules
- Networking, base URL, and credential-handling rules
- Documentation update expectations

Read: `docs/architecture/conventions.md`
