# KMPLibraryStarter - KMP UI Library Architecture

A Kotlin Multiplatform library template for building cross-platform UI components for Android, iOS, and JVM platforms.

## Overview

KMPLibraryStarter is a starter template for building cross-platform UI libraries using Compose Multiplatform. It follows Clean Architecture principles with clear separation between data, domain, and presentation layers.

## Supported Platforms

| Platform | SDK/Version | Notes |
|----------|-------------|-------|
| Android | minSdk 26, compileSdk 36 | Jetpack Compose |
| iOS | arm64, x64, simulatorArm64 | Compose Multiplatform |
| JVM | Java 11+ | Desktop/Server |

## Module Structure

```
KMPLibraryStarter/
├── build-logic/           # Gradle convention plugins
│   └── convention/        # Shared build configuration
├── core/
│   ├── common/            # Shared utilities and extensions
│   ├── data/              # Data layer (repositories, database, network)
│   ├── datastore/         # User preferences and settings
│   ├── designsystem/      # UI theme, colors, typography
│   ├── domain/            # Business logic and use cases
│   ├── testing/           # Test utilities and doubles
│   └── ui/                # Compose UI components
├── config/                # Detekt configuration
└── gradle/                # Version catalog
```

## Module Dependencies

```
                    ┌─────────────┐
                    │   core:ui   │
                    └──────┬──────┘
           ┌───────────────┼───────────────┐
           │               │               │
           ▼               ▼               ▼
   ┌──────────────┐ ┌─────────────┐ ┌─────────────┐
   │designsystem  │ │   domain    │ │   common    │
   └──────────────┘ └──────┬──────┘ └─────────────┘
                           │               ▲
                           ▼               │
                    ┌─────────────┐        │
                    │    data     │────────┘
                    └──────┬──────┘
                           │
                           ▼
                    ┌─────────────┐
                    │  datastore  │
                    └─────────────┘
```

### Dependency Rules

- **api**: Exposed to consumers (transitive)
- **implementation**: Internal only (not transitive)

| Module | Dependencies |
|--------|--------------|
| `core:ui` | api: designsystem, impl: domain, common |
| `core:domain` | api: common, paging, serialization |
| `core:data` | api: domain, impl: common, datastore |
| `core:designsystem` | Compose libraries only |
| `core:datastore` | DataStore, Serialization |
| `core:common` | Coroutines, DateTime |
| `core:testing` | api: common, KoTest, Coroutines Test |

## Build System

### Convention Plugins

Located in `build-logic/convention/`, these provide consistent configuration:

| Plugin ID | Purpose |
|-----------|---------|
| `kmp.library` | Base KMP library setup |
| `kmp.compose` | KMP + Compose Multiplatform |
| `kmp.jacoco` | Code coverage reporting |
| `kmp.lint` | Android lint configuration |
| `kmp.detekt` | Static analysis |

### Usage in Modules

```kotlin
// build.gradle.kts
plugins {
  alias(libs.plugins.kmp.library)  // For non-UI modules
  // OR
  alias(libs.plugins.kmp.compose)  // For Compose modules
}
```

### Version Catalog

All dependencies are centralized in `gradle/libs.versions.toml`:

```toml
[versions]
kotlin = "2.2.21"
compose-multiplatform = "1.10.0-beta02"
coroutines = "1.10.2"

[libraries]
kotlinx-coroutines-core = { module = "...", version.ref = "coroutines" }

[plugins]
kmp-library = { id = "kmp.library" }
```

## Core Modules

### core:common

Shared utilities available to all modules.

**Key Components:**

```kotlin
// Extension functions
fun Int?.orZero(): Int
fun <T> T?.require(): T
fun <T> Flow<T?>?.orEmpty(): Flow<T?>

// Coroutine dispatchers (interface + expect/actual)
interface DispatcherProviderContract {
  fun io(): CoroutineDispatcher
  fun main(): CoroutineDispatcher
  fun default(): CoroutineDispatcher
}

expect class DispatcherProvider() : DispatcherProviderContract
```

**Platform Implementations:**

| Platform | io() | main() | default() |
|----------|------|--------|-----------|
| Android | Dispatchers.IO | Dispatchers.Main | Dispatchers.Default |
| iOS | Dispatchers.Default | Dispatchers.Main | Dispatchers.Default |
| JVM | Dispatchers.IO | Dispatchers.Main | Dispatchers.Default |

### core:designsystem

Material 3 design system with custom theming.

**Theme Usage:**

```kotlin
@Composable
fun MyApp() {
  KmpMaterialTheme {
    // Access theme values
    val colors = KmpTheme.colors           // M3 ColorScheme
    val kmpColors = KmpTheme.kmpColors     // Custom colors
    val typography = KmpTheme.typography
    val shapes = KmpTheme.shapes
  }
}

// For media/dark contexts
MediaTheme {
  // Dark theme variant
}
```

**Color Palette:**

| Category | Colors |
|----------|--------|
| Primary | Green500, Green600 |
| Neutral | Grey50-600 |
| Status | WarningRed500, WarningOrange, InfoBlue |
| Special | PlanApprovalColors, AiAssistantColors, RichTextColors |

**Typography Scale:**

- Display: Large, Medium, Small
- Headline: Large, Medium, Small
- Title: Large, Medium, Small
- Body: Large, Medium, Small
- Label: Large, Medium, Small

### core:domain

Business logic layer (interfaces and use cases).

**Conventions:**
- Define repository interfaces here
- Implement use cases as single-responsibility classes
- Use `@Inject` for dependency injection

### core:data

Data layer implementation.

**Database (SQLDelight):**

```sql
-- User.sq
CREATE TABLE UserEntity (
  id TEXT NOT NULL PRIMARY KEY,
  name TEXT NOT NULL,
  email TEXT,
  createdAt INTEGER NOT NULL
);

selectAll: SELECT * FROM UserEntity;
selectById: SELECT * FROM UserEntity WHERE id = ?;
insert: INSERT OR REPLACE INTO UserEntity(id, name, email, createdAt) VALUES (?, ?, ?, ?);
```

**Platform Drivers:**

| Platform | Driver |
|----------|--------|
| Android | SQLDelight Android Driver |
| iOS | SQLDelight Native Driver |
| JVM | SQLDelight JDBC Driver (SQLite) |

**Networking (Ktor):**

| Platform | HTTP Engine |
|----------|-------------|
| Android | OkHttp |
| iOS | Darwin |
| JVM | OkHttp |

### core:datastore

User preferences using AndroidX DataStore.

```kotlin
@Serializable
data class UserPreferences(
  val isLoggedIn: Boolean = false,
  val userId: String? = null,
)
```

### core:ui

Compose Multiplatform UI components.

**Dependencies:**
- Compose: runtime, foundation, material3, ui, resources
- Navigation: androidx.navigation.compose
- Image loading: Coil 3
- Paging: AndroidX Paging + Compose
- ViewModel: JetBrains Lifecycle

### core:testing

Test utilities and doubles.

```kotlin
class TestDispatcherProvider(
  private val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : DispatcherProviderContract {
  override fun io(): CoroutineDispatcher = testDispatcher
  override fun main(): CoroutineDispatcher = testDispatcher
  override fun default(): CoroutineDispatcher = testDispatcher
}
```

**Testing Stack:**
- Framework: KoTest (FunSpec)
- Mocking: MockK
- Coroutines: kotlinx-coroutines-test

## Dependency Injection

Uses **kotlin-inject** for compile-time DI.

```kotlin
@Inject
class MyRepository(
  private val database: UserDatabase,
  private val dispatchers: DispatcherProviderContract,
)
```

KSP processors configured for all targets:
- kspAndroid
- kspIosArm64
- kspIosSimulatorArm64
- kspIosX64
- kspJvm

## Code Quality

### Static Analysis (Detekt)

Configuration: `config/detekt/detekt.yml`

Key thresholds:
- Cyclomatic complexity: 15
- Large class: 600 lines
- Long method: 60 lines
- Long parameter list: 6 (functions), 7 (constructors)

### Formatting (Spotless + KtLint)

- Max line length: 100 characters
- Trailing commas: allowed
- Wildcard imports: disabled

### Code Coverage (Jacoco)

Per-variant coverage reports generated via `kmp.jacoco` plugin.

## Adding a New Module

1. Create directory under `core/`:
```
core/
└── newmodule/
    ├── build.gradle.kts
    └── src/
        ├── commonMain/kotlin/
        ├── androidMain/kotlin/
        ├── iosMain/kotlin/
        └── jvmMain/kotlin/
```

2. Add to `settings.gradle.kts`:
```kotlin
include(":core:newmodule")
```

3. Configure `build.gradle.kts`:
```kotlin
plugins {
  alias(libs.plugins.kmp.library)
  // OR for Compose modules:
  alias(libs.plugins.kmp.compose)
}

android {
  namespace = "com.sermilion.kmpstarter.core.newmodule"
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.core.common)
    }
  }
}
```

## Consuming the Library

### Android

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
  repositories {
    // Add repository where library is published
  }
}

// build.gradle.kts
dependencies {
  implementation("com.sermilion:kmpstarter-core-ui:VERSION")
}
```

### iOS (via CocoaPods or SPM)

Configure in your iOS project after publishing the framework.

### JVM

```kotlin
dependencies {
  implementation("com.sermilion:kmpstarter-core-ui-jvm:VERSION")
}
```

## Development Setup

### Prerequisites

- JDK 11+
- Android Studio or IntelliJ IDEA
- Xcode (for iOS development)

### Build Commands

```bash
# Build all modules
./gradlew build

# Run tests
./gradlew check

# Android-specific
./gradlew :core:ui:assembleDebug

# iOS framework
./gradlew :core:ui:linkDebugFrameworkIosArm64

# Code coverage
./gradlew jacocoTestReport
```

### IDE Setup

1. Open project in Android Studio or IntelliJ
2. Sync Gradle
3. For iOS: Open `iosApp/` in Xcode

## Package Structure

```
com.sermilion.kmpstarter/
├── common/
│   └── coroutines/
│       ├── DispatcherProviderContract
│       ├── DispatcherProvider
│       └── Flows
├── core/
│   ├── data/
│   │   └── db/
│   ├── datastore/
│   ├── designsystem/
│   │   └── theme/
│   ├── domain/
│   ├── testing/
│   └── ui/
```

## Configuration Files

| File | Purpose |
|------|---------|
| `gradle.properties` | JVM args, Kotlin settings |
| `gradle/libs.versions.toml` | Dependency versions |
| `config/detekt/detekt.yml` | Static analysis rules |
| `build-logic/` | Convention plugins |

## Key Decisions

1. **Compose Multiplatform** over platform-specific UI for maximum code sharing
2. **kotlin-inject** over Koin/Hilt for compile-time safety and multiplatform support
3. **SQLDelight** over Room for true multiplatform database
4. **Ktor** over Retrofit for multiplatform networking
5. **KoTest** over JUnit for better Kotlin DSL and multiplatform support
6. **Convention plugins** over shared buildSrc for better isolation and caching
