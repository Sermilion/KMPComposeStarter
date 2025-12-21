# KMP Compose Starter

A production-ready Kotlin Compose Multiplatform starter template for building cross-platform applications. This project provides a solid foundation with modern architecture patterns, dependency injection, type-safe navigation, and comprehensive tooling.

Use this as a starting point for your next KMP project - clone, rename the package, and start building your features.

## Platforms

- Android (API 26+)
- iOS (arm64, x64, simulatorArm64)
- Desktop (JVM)

## Features

- Multi-module clean architecture
- kotlin-inject + Anvil for compile-time dependency injection
- Navigation 3 with type-safe routes
- SQLDelight for local persistence
- Ktor for networking
- Kotest + MockK for testing
- Detekt + Spotless for code quality
- Convention plugins for consistent module setup

## Architecture

```
KMPComposeStarter/
├── composeApp/              # Main application module
│   ├── androidMain/         # Android entry points (MainActivity, Application)
│   ├── iosMain/             # iOS entry points (MainViewController)
│   ├── jvmMain/             # Desktop entry point (Main.kt)
│   └── commonMain/          # Shared App.kt, navigation
├── core/
│   ├── common/              # Utilities, DI scopes, dispatchers
│   ├── data/                # Repositories, API services, database
│   ├── datastore/           # User preferences
│   ├── designsystem/        # Theme, colors, typography
│   ├── domain/              # Business logic, use cases
│   ├── testing/             # Test utilities
│   └── ui/                  # Shared UI components
├── feature/
│   ├── auth/                # Login and registration screens
│   ├── home/                # Home screen
│   ├── profile/             # User profile screen
│   └── settings/            # Settings screen
└── build-logic/             # Convention plugins
```

## DI Architecture (kotlin-inject + Anvil)

### Scope Hierarchy

```
AppScope (Application singleton)
    └── UserScope (Login session)
            └── UserFeatureScope (Per screen)
```

### Key Components

- **ApplicationComponent**: Platform-specific (Android, iOS, JVM), marked with `@MergeComponent(AppScope::class)`
- **UserComponent**: Created on login, destroyed on logout, `@ContributesSubcomponent(UserScope::class)`
- **ScreenComponent**: Per navigation destination, `@ContributesSubcomponent(UserFeatureScope::class)`

### Creating a ViewModel

```kotlin
@Inject
@SingleIn(UserFeatureScope::class)
class MyViewModel(
  private val repository: MyRepository,
  private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
  // ViewModel implementation
}
```

## Navigation 3

Uses JetBrains Navigation 3 (`org.jetbrains.androidx.navigation3:navigation3-ui`) with:

- Type-safe routes using `@Serializable` data objects
- `NavDisplay` with user-managed back stack
- Polymorphic serialization for multiplatform support

### Defining Routes

```kotlin
@Serializable
sealed interface AppRoute : NavKey

@Serializable
data object HomeRoute : AppRoute

@Serializable
data class DetailRoute(val id: String) : AppRoute
```

## Key Technologies

| Category | Technology |
|----------|------------|
| UI | Compose Multiplatform 1.10.0-beta02 |
| Kotlin | 2.2.21 |
| DI | kotlin-inject 0.8.0 + Anvil 0.1.6 |
| Navigation | Navigation 3 (1.0.0-alpha06) |
| Database | SQLDelight 2.2.1 |
| Networking | Ktor 3.3.2 |
| Testing | Kotest 5.9.1, MockK 1.14.6 |

## Getting Started

### Prerequisites

- JDK 11 or higher
- Android Studio Hedgehog (2023.1.1) or newer
- Xcode 15+ (for iOS development)

### Android

```bash
./gradlew :composeApp:assembleDebug
```

### iOS

Open `iosApp/iosApp.xcodeproj` in Xcode and run.

### Desktop

```bash
./gradlew :composeApp:run
```

## Demo Credentials

- Email: `test@test.com`
- Password: `password`

## Convention Plugins

| Plugin | Purpose |
|--------|---------|
| `kmp.library` | Base KMP library setup |
| `kmp.compose` | KMP + Compose Multiplatform |
| `kmp.application` | KMP application (composeApp) |
| `kmp.jacoco` | Code coverage |
| `kmp.detekt` | Static analysis |

### Usage

```kotlin
plugins {
  alias(libs.plugins.kmp.library)    // For non-UI modules
  alias(libs.plugins.kmp.compose)    // For Compose modules
  alias(libs.plugins.kmp.application) // For app module
}
```

## Code Quality

```bash
# Run static analysis
./gradlew detekt

# Format code
./gradlew spotlessApply

# Run tests
./gradlew check
```

## Adding a Feature Module

1. Create directory: `feature/myfeature/`
2. Add to `settings.gradle.kts`: `include(":feature:myfeature")`
3. Create `build.gradle.kts`:

```kotlin
plugins {
  alias(libs.plugins.kmp.compose)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.sermilion.kmpcomposestarter.feature.myfeature"
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.core.common)
      implementation(projects.core.domain)
      implementation(projects.core.designsystem)
    }
  }
}

dependencies {
  add("kspAndroid", libs.kotlin.inject.compiler)
  add("kspAndroid", libs.kotlin.inject.anvil.compiler)
  // ... other platforms
}
```

## Resources

- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Navigation 3](https://kotlinlang.org/docs/multiplatform/compose-navigation-3.html)
- [kotlin-inject](https://github.com/evant/kotlin-inject)
- [kotlin-inject-anvil](https://github.com/amzn/kotlin-inject-anvil)

## License

MIT
