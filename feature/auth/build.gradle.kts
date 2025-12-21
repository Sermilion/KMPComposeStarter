plugins {
  alias(libs.plugins.kmp.compose)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.sermilion.kmpcomposestarter.feature.auth"
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.core.common)
      implementation(projects.core.domain)
      implementation(projects.core.data)
      implementation(projects.core.designsystem)
      implementation(projects.core.ui)

      implementation(libs.kotlinx.collections.immutable)
      implementation(libs.jetbrains.lifecycle.viewmodel)
      implementation(libs.jetbrains.lifecycle.viewmodel.compose)
      implementation(libs.kermit)
    }
  }

  compilerOptions {
    freeCompilerArgs.add("-Xexpect-actual-classes")
  }
}

dependencies {
  add("kspAndroid", libs.kotlin.inject.compiler)
  add("kspAndroid", libs.kotlin.inject.anvil.compiler)
  add("kspIosArm64", libs.kotlin.inject.compiler)
  add("kspIosArm64", libs.kotlin.inject.anvil.compiler)
  add("kspIosSimulatorArm64", libs.kotlin.inject.compiler)
  add("kspIosSimulatorArm64", libs.kotlin.inject.anvil.compiler)
  add("kspIosX64", libs.kotlin.inject.compiler)
  add("kspIosX64", libs.kotlin.inject.anvil.compiler)
  add("kspJvm", libs.kotlin.inject.compiler)
  add("kspJvm", libs.kotlin.inject.anvil.compiler)
}
