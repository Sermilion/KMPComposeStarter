plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.kmp.jacoco)
  alias(libs.plugins.ksp)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.compose.compiler)
}

kotlin {
  compilerOptions {
    freeCompilerArgs.add("-Xexpect-actual-classes")
  }

  sourceSets {
    commonMain.dependencies {
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.kotlin.inject.runtime)
      api(libs.jetbrains.lifecycle.viewmodel)
      api(libs.jetbrains.lifecycle.viewmodel.compose)
      implementation(compose.runtime)
      api(libs.kermit)
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

    androidUnitTest.dependencies {
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
  add("kspIosArm64", libs.kotlin.inject.compiler)
  add("kspIosSimulatorArm64", libs.kotlin.inject.compiler)
  add("kspIosX64", libs.kotlin.inject.compiler)
  add("kspJvm", libs.kotlin.inject.compiler)
}

android {
  namespace = "com.sermilion.kmpstarter.core.common"

  testOptions {
    unitTests.all {
      it.useJUnitPlatform()
    }
  }
}

tasks.named<Test>("jvmTest") {
  useJUnitPlatform()
}
