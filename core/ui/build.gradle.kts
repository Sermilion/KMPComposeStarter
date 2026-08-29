plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.serialization)
}

compose.resources {
  publicResClass = true
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.core.common)
      api(projects.core.designsystem)
      implementation(projects.core.domain)

      implementation(libs.compose.runtime)
      implementation(libs.compose.foundation)
      implementation(libs.compose.material3)
      implementation(libs.compose.ui)
      implementation(libs.compose.components.resources)
      implementation(libs.serialization.json)
      implementation(libs.kotlinx.collections.immutable)
      implementation(libs.kermit)
      implementation(libs.coil.kt)
      implementation(libs.coil.kt.compose)
      implementation(libs.coil.kt.network.ktor3)
      implementation(libs.kotlinx.datetime)
      implementation(libs.jetbrains.lifecycle.viewmodel)
      implementation(libs.jetbrains.lifecycle.viewmodel.compose)
      implementation(libs.kotlin.inject.runtime)
    }

    jvmTest.dependencies {
      implementation(libs.kotest.framework.engine)
      implementation(libs.kotest.assertions.core)
      implementation(libs.kotest.runner.junit5.jvm)
      implementation(libs.kotlinx.coroutines.test)
    }
  }
}

// Compose generates the `Res` accessors into commonMain, but the KSP tasks read the source roots
// through a file collection that does not always carry the generator dependency, so KSP can run
// before `Res` exists. Compose's generator tasks are `internal` to its Gradle plugin and cannot be
// referenced by type from here, so they are matched by name. Mirrors the explicit ordering
// core:data declares for its generated NetworkConfig.
val composeResourceGenerators = tasks.matching {
  it.name.startsWith("generateResourceAccessors") ||
    it.name.startsWith("generateComposeResClass") ||
    it.name.startsWith("generateExpectResourceCollectors") ||
    it.name.startsWith("generateActualResourceCollectors")
}

tasks.matching { it.name.startsWith("ksp") }.configureEach {
  dependsOn(composeResourceGenerators)
}
