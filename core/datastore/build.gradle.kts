plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.core.domain)
      implementation(projects.core.common)
      // api: DataStore<UserPreferences> and okio Path are part of this module's own contract.
      api(libs.androidx.datastore.core)
      api(libs.okio)
      implementation(libs.androidx.datastore.core.okio)
      implementation(libs.serialization.json)
      implementation(libs.kermit)
      implementation(libs.kotlin.inject.runtime)
      implementation(libs.kotlin.inject.anvil.runtime)
    }

    androidMain.dependencies {
      implementation(libs.kotlinx.coroutines.android)
    }

    commonTest.dependencies {
      implementation(libs.kotest.assertions.core)
      implementation(libs.kotest.framework.engine)
      implementation(libs.kotest.framework.datatest)
      implementation(libs.kotlinx.coroutines.test)
      implementation(kotlin("test"))
    }

    jvmTest.dependencies {
      implementation(projects.core.testing)
      implementation(libs.kotest.runner.junit5.jvm)
    }
  }
}
