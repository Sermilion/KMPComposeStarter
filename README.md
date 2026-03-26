# NotesApp

NotesApp is a Kotlin Multiplatform note-taking app built around a chat-with-yourself mental model.

Instead of folders and forms first, the core idea is simple: create threads, keep sending yourself notes, and organize thoughts the same way you would drop messages into Telegram or WhatsApp. The long-term direction is to add AI-aware note actions so tagged notes can trigger useful automation without changing the lightweight capture flow.

## Current Product Direction

- Shared Compose Multiplatform app for Android, iOS, and desktop JVM
- Readian visual language for colors, typography, onboarding, and auth screens
- Thread-first note capture UX
- Readian account authentication so existing Readian users can sign in here too
- Multi-module architecture designed to keep future AI and note features isolated by domain

## Supported Platforms

- Android (minSdk 26, compileSdk 36)
- iOS (`arm64`, `simulatorArm64`)
- Desktop JVM

## Repository Layout

```text
NotesApp/
|- androidApp/                    # Android launcher, manifest, and app bootstrap
|- composeApp/                    # Shared app shell plus iOS/JVM entry points
|- core/
|  |- common/                     # Cross-cutting utilities, DI scopes, dispatchers, navigation contracts
|  |- data/                       # Repositories, local/remote data sources, and persistence
|  |- datastore/                  # Lightweight persistent settings
|  |- designsystem/               # Readian-inspired theme and shared UI primitives
|  |- domain/                     # Domain contracts and screen-component boundaries
|  |- testing/                    # Shared test helpers
|  \- ui/                         # Shared UI helpers and screen-component integration
|- feature/
|  |- auth/                       # Login and registration flows
|  |- onboarding/                 # Welcome/onboarding entry flow
|  |- home/                       # Main signed-in experience
|  |- profile/
|  \- settings/
|- codegen/viewmodel-inject-processor/  # KSP codegen for scoped ViewModel wiring
|- build-logic/convention/        # Reusable Gradle convention plugins
\- docs/                          # Contributor and architecture documentation
```

## Architecture Notes

- `composeApp` owns navigation and platform entry points
- `core:*` modules provide shared infrastructure and abstractions
- `feature:*` modules own product-facing flows and UI
- Auth-only and signed-in navigation stay separated through explicit back stack ownership
- The codebase is structured so auth can later move into a shared library used by multiple Readian apps

## Main Commands

```bash
./gradlew check
./gradlew build
./gradlew detekt
./gradlew spotlessApply
./gradlew :androidApp:assembleDebug
./gradlew :composeApp:linkDebugFrameworkIosArm64
./gradlew :composeApp:run
```

## Contributor Docs

Start with `docs/ARCHITECTURE.md`, then use the focused docs under `docs/architecture/` for navigation, persistence, DI, and build details.

Repository-specific workflow rules for humans and agents live in `AGENTS.md`.
