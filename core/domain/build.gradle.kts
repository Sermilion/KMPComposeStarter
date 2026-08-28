plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.ksp)
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
