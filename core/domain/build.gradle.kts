plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.ksp)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      // api: core:common types (scopes, ScreenComponentProvider) appear in this module's own
      // public contracts, so consumers need them on the compile classpath.
      api(projects.core.common)
      // api: StateFlow and CoroutineScope appear in this module's own public contracts
      // (AuthRepository, UserComponentManager, UserSessionScope).
      api(libs.kotlinx.coroutines.core)
    }

    androidMain.dependencies {
      implementation(libs.kotlinx.coroutines.android)
    }

    jvmTest.dependencies {
      implementation(projects.core.testing)
      implementation(libs.kotest.framework.engine)
      implementation(libs.kotest.assertions.core)
      implementation(libs.kotest.runner.junit5.jvm)
      implementation(libs.kotlinx.coroutines.test)
      implementation(libs.mockk.core)
    }
  }
}
