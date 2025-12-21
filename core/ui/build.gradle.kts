plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.kmp.jacoco)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.ksp)
  id("org.jetbrains.kotlin.plugin.serialization")
}

android {
  namespace = "com.sermilion.kmpstarter.core.ui"
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

      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(libs.serialization.json)
      implementation(libs.kotlinx.collections.immutable)
      implementation(libs.kermit)
      implementation(libs.coil.kt)
      implementation(libs.coil.kt.compose)
      implementation(libs.coil.kt.network.ktor3)
      implementation(libs.kotlinx.datetime)
      implementation(libs.paging.common)
      implementation(libs.paging.compose)
      implementation(libs.androidx.navigation.compose)
      implementation(libs.jetbrains.lifecycle.viewmodel)
      implementation(libs.jetbrains.lifecycle.viewmodel.compose)
      implementation(libs.kotlin.inject.runtime)
    }
  }
}

dependencies {
  add("kspAndroid", libs.kotlin.inject.compiler)
  add("kspIosArm64", libs.kotlin.inject.compiler)
  add("kspIosSimulatorArm64", libs.kotlin.inject.compiler)
  add("kspIosX64", libs.kotlin.inject.compiler)
  add("kspJvm", libs.kotlin.inject.compiler)
}

tasks.matching { it.name.contains("ksp") && it.name.contains("Kotlin") }.configureEach {
  dependsOn(tasks.matching { it.name.startsWith("generateResourceAccessors") })
  dependsOn(tasks.matching { it.name.contains("generateComposeResClass") })
  dependsOn(tasks.matching { it.name.contains("generateActualResourceCollectors") })
  dependsOn(tasks.matching { it.name.contains("generateExpectResourceCollectors") })
}
