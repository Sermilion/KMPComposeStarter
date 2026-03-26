# NotesApp

A production-ready Kotlin Multiplatform starter for Compose applications across Android, iOS, and desktop JVM.

This repository gives you a modern baseline for app development with Compose Multiplatform, kotlin-inject + Anvil, Navigation 3, Room 3, structured architecture docs, and shared build logic.

## Supported Platforms

- Android (minSdk 26, compileSdk 36)
- iOS (`arm64`, `simulatorArm64`)
- Desktop JVM

## Highlights

- Multi-module app architecture with clear `core` and `feature` boundaries
- Compile-time dependency injection with `kotlin-inject` + Anvil
- Navigation 3 with typed routes and explicit back stack ownership
- Room 3 persistence with shared schemas and platform-specific database builders
- Shared Gradle convention plugins in `build-logic/`
- Kotest, MockK, Detekt, Spotless, and GitHub automation

## Repository Layout

```text
NotesApp/
|- androidApp/                    # Android-only application module and manifest/resources
|- composeApp/                    # Shared app shell plus iOS/JVM entry points
|- core/
|  |- common/                     # Cross-cutting utilities, DI scopes, dispatchers, navigation contracts
|  |- data/                       # Room 3 databases, repositories, remote/local data sources
|  |- datastore/                  # Preferences and lightweight persistent settings
|  |- designsystem/               # Theme, tokens, and shared UI styling
|  |- domain/                     # Domain contracts, screen components, shared business logic
|  |- testing/                    # Shared test helpers
|  \- ui/                         # Shared UI helpers and screen-component integration
|- feature/
|  |- auth/
|  |- home/
|  |- profile/
|  \- settings/
|- codegen/viewmodel-inject-processor/  # KSP codegen for scoped ViewModel wiring
|- build-logic/convention/        # Reusable Gradle convention plugins
\- docs/                          # Structured contributor and architecture documentation
```

## Architecture Docs

Start with `docs/ARCHITECTURE.md`.

The architecture docs are split into focused files so contributors and agents can read only the parts they need:

- `docs/architecture/overview.md`
- `docs/architecture/modules.md`
- `docs/architecture/di-and-scoping.md`
- `docs/architecture/navigation.md`
- `docs/architecture/persistence.md`
- `docs/architecture/build-and-tooling.md`
- `docs/architecture/conventions.md`

The root `ARCHITECTURE.md` remains as a lightweight entry point for tools and humans that expect it at the repository root.

## Getting Started

### Prerequisites

- JDK 17
- Latest stable Android Studio or IntelliJ IDEA
- Latest stable Xcode for iOS development

### Main Commands

```bash
# Main quality gate
./gradlew check

# Android app
./gradlew :androidApp:assembleDebug

# iOS framework for device
./gradlew :composeApp:linkDebugFrameworkIosArm64

# Desktop app
./gradlew :composeApp:run
```

### iOS Host App

Open `iosApp/iosApp.xcodeproj` in Xcode and run the generated framework from there.

## Key Technologies

| Category | Technology |
|----------|------------|
| Kotlin | 2.3.10 |
| Compose Multiplatform | 1.10.2 |
| Android Gradle Plugin | 9.0.1 |
| Dependency Injection | kotlin-inject 0.8.0 + Anvil 0.1.6 |
| Navigation | Navigation 3 alpha06 |
| Persistence | Room 3 alpha01 |
| Networking | Ktor 3.3.2 |
| Testing | Kotest 5.9.1, MockK 1.14.6 |

## Build Notes

The project is updated to AGP 9 and Gradle 9.1.0.

Android now lives in a dedicated `androidApp` module, while shared multiplatform modules use the Android KMP library plugin.

`core:data` intentionally disables the `RestrictedApi` lint check because Room 3 `alpha01` currently reports false positives for both generated KSP code and `RoomDatabase` usage in KMP lint tasks. See `docs/architecture/build-and-tooling.md` for details.

## Demo Credentials

- Email: `test@test.com`
- Password: `password`

## Resources

- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Navigation 3](https://kotlinlang.org/docs/multiplatform/compose-navigation-3.html)
- [Room 3](https://developer.android.com/kotlin/multiplatform/room)
- [kotlin-inject](https://github.com/evant/kotlin-inject)
- [kotlin-inject-anvil](https://github.com/amzn/kotlin-inject-anvil)

## License

MIT
