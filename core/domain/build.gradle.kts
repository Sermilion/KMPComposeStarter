// No KSP: this module declares no DI annotations. The scope annotations, the ViewModel factory and
// the screen component that do live in `core:common`, which keeps this layer framework-light.
plugins {
  alias(libs.plugins.kmp.library)
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
  }
}
