// A dependency-only module: it carries no source of its own and exists to `api`-export one test
// stack (kotest, coroutines-test, MockK) to :core:data, :core:datastore and :core:domain. Deleting
// it because it looks empty takes those three modules' test dependencies with it.
plugins {
  alias(libs.plugins.kmp.library)
}

kotlin {
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

    // Note: iOS testing doesn't include MockK as it's not supported on Kotlin Native.
    // Options for iOS mocking:
    // 1. Add a multiplatform mocking library such as Mokkery to the version catalog
    // 2. Create manual test doubles
    // 3. Use real implementations with test configurations
    // 4. Use fake implementations that implement the same interface
  }
}
