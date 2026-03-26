plugins {
  alias(libs.plugins.kmp.library)
}

kotlin {
  android {
    namespace = "news.readian.notoesapp.core.testing"
    compileSdk =
      libs.versions.compileSdk
        .get()
        .toInt()
    minSdk =
      libs.versions.minSdk
        .get()
        .toInt()
    withHostTestBuilder {}
    androidResources {
      enable = true
    }
  }

  sourceSets {
    commonMain.dependencies {
      api(projects.core.common)
      implementation(libs.kotlinx.coroutines.core)
      api(libs.kotlinx.coroutines.test)
      api(libs.kotest.assertions.core)
    }

    androidMain.dependencies {
      api(libs.androidx.junit)
      api(libs.mockk.android)
    }

    jvmMain.dependencies {
      api(libs.mockk.core)
    }

    // Note: iOS testing doesn't include MockK as it's not supported on Kotlin Native
    // Options for iOS mocking:
    // 1. Use Mokkery (multiplatform mocking library) - already in version catalog
    // 2. Create manual test doubles
    // 3. Use real implementations with test configurations
    // 4. Use fake implementations that implement the same interface
  }
}
