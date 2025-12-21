plugins {
  alias(libs.plugins.kmp.compose)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kmp.kotlininject)
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

    jvmTest.dependencies {
      implementation(libs.kotest.framework.engine)
      implementation(libs.kotest.assertions.core)
      implementation(libs.kotest.runner.junit5.jvm)
      implementation(libs.kotlinx.coroutines.test)
      implementation(libs.mockk.core)
    }
  }

  compilerOptions {
    freeCompilerArgs.add("-Xexpect-actual-classes")
  }
}

tasks.named<Test>("jvmTest") {
  useJUnitPlatform()
}
