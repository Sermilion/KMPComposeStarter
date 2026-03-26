# NotesApp Architecture

Use `docs/ARCHITECTURE.md` as the canonical entry point for repository architecture and contributor guidance.

This root file stays intentionally small so tools that look for a top-level `ARCHITECTURE.md` can immediately jump to the structured docs without parsing outdated duplicated content.

## Read First

1. `docs/ARCHITECTURE.md`
2. `docs/architecture/overview.md`
3. `docs/architecture/modules.md`
4. `docs/architecture/di-and-scoping.md`
5. `docs/architecture/navigation.md`
6. `docs/architecture/persistence.md`
7. `docs/architecture/build-and-tooling.md`
8. `docs/architecture/conventions.md`

## Current Architecture Summary

- `composeApp` hosts the shared app shell and platform entry points.
- `core` modules provide shared infrastructure, UI, data, domain, and testing support.
- `feature` modules contain app-facing screens and screen-scoped logic.
- Dependency injection is organized around `AppScope`, `UserScope`, and `ScreenScope`.
- Navigation is type-safe and back-stack driven through `StarterNavigationState` and `StarterNavigator`.
- Local persistence now uses Room 3 instead of SQLDelight.
- Build tooling is centralized in `build-logic/` and `gradle/libs.versions.toml`.

For the details, read the focused docs under `docs/architecture/`.
