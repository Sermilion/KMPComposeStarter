plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.kmp.jacoco)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  android {
    namespace = "com.sermilion.kmpcomposestarter.core.domain"
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
      api(libs.paging.common)
      api(libs.serialization.json)
      api(libs.kotlin.inject.runtime)
      implementation(libs.kermit)
    }
    androidMain.dependencies {
      implementation(libs.kotlinx.coroutines.android)
      implementation(libs.paging.runtime)
      implementation(libs.paging.compose)
    }
  }

  compilerOptions {
    freeCompilerArgs.add("-Xexpect-actual-classes")
  }
}

tasks.named<Test>("jvmTest") {
  useJUnitPlatform()
}

kotlin {
  sourceSets {
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
