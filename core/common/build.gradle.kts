plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.ksp)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.kotest)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.kotlin.inject.runtime)
      api(libs.kotlin.inject.anvil.runtime)
      api(libs.kotlin.inject.anvil.runtime.optional)
      api(libs.jetbrains.lifecycle.viewmodel)
      api(libs.jetbrains.lifecycle.viewmodel.compose)
      implementation(libs.compose.runtime)
      api(libs.kermit)
      api(libs.navigation3.ui)
      api(libs.serialization.json)
    }

    androidMain.dependencies {
      implementation(libs.kotlinx.coroutines.android)
    }

    // Specs here run on every target. Kotest 6 discovers them on Kotlin/Native without a compiler
    // plugin, which is why the shared logic is tested on iOS and not only on the JVM.
    commonTest.dependencies {
      implementation(libs.kotest.assertions.core)
      implementation(libs.kotest.framework.engine)
      implementation(libs.kotlinx.coroutines.test)
      implementation(kotlin("test"))
    }

    // The JVM-backed targets inherit the commonTest specs and discover them through the JUnit
    // Platform, so each needs the runner.
    jvmTest.dependencies {
      implementation(libs.kotest.runner.junit5.jvm)
    }

    getByName("androidHostTest").dependencies {
      implementation(libs.kotest.runner.junit5.jvm)
    }
  }
}
