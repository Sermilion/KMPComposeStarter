plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.kmp.jacoco)
  alias(libs.plugins.ksp)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  android {
    namespace = "news.readian.notoesapp.core.common"
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

  compilerOptions {
    freeCompilerArgs.add("-Xexpect-actual-classes")
  }

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
      implementation(libs.core.ktx)
    }

    commonTest.dependencies {
      implementation(libs.kotest.assertions.core)
      implementation(libs.kotest.framework.engine)
      implementation(libs.kotest.framework.datatest)
      implementation(libs.kotlinx.coroutines.test)
      implementation(kotlin("test"))
    }

    getByName("androidHostTest").dependencies {
      implementation(libs.androidx.junit)
      implementation(libs.kotest.runner.junit5.jvm)
    }

    jvmTest.dependencies {
      implementation(libs.kotest.runner.junit5.jvm)
    }
  }
}

dependencies {
  add("kspAndroid", libs.kotlin.inject.compiler)
  add("kspAndroid", libs.kotlin.inject.anvil.compiler)
  add("kspIosArm64", libs.kotlin.inject.compiler)
  add("kspIosArm64", libs.kotlin.inject.anvil.compiler)
  add("kspIosSimulatorArm64", libs.kotlin.inject.compiler)
  add("kspIosSimulatorArm64", libs.kotlin.inject.anvil.compiler)
  add("kspJvm", libs.kotlin.inject.compiler)
  add("kspJvm", libs.kotlin.inject.anvil.compiler)
}

tasks.named<Test>("jvmTest") {
  useJUnitPlatform()
}
