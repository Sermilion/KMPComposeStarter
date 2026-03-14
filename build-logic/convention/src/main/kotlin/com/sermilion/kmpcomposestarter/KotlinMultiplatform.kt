package com.sermilion.kmpcomposestarter

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinMultiplatform(
  extension: KotlinMultiplatformExtension
) {
  extension.apply {
    jvmToolchain(17)

    compilerOptions {
      freeCompilerArgs.addAll(
        listOf(
          "-opt-in=kotlin.RequiresOptIn",
          "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
          "-opt-in=kotlinx.coroutines.FlowPreview",
          "-opt-in=kotlin.ExperimentalMultiplatform",
          "-Xexpect-actual-classes",
        )
      )
    }

    jvm()

    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    sourceSets.apply {
      commonMain {
        dependencies {
        }
      }

      commonTest {
        dependencies {
        }
      }
    }
  }

  configureKotlin()
}

internal fun Project.configureKotlinMultiplatformCompose(
  extension: KotlinMultiplatformExtension
) {
  extension.apply {
    jvmToolchain(17)

    compilerOptions {
      freeCompilerArgs.addAll(
        listOf(
          "-opt-in=kotlin.RequiresOptIn",
          "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
          "-opt-in=kotlinx.coroutines.FlowPreview",
          "-opt-in=kotlin.ExperimentalMultiplatform",
          "-Xexpect-actual-classes",
        )
      )
    }

    jvm()

    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    sourceSets.apply {
      commonMain {
        dependencies {
        }
      }

      androidMain {
        dependencies {
        }
      }

      iosMain {
        dependencies {
        }
      }
    }
  }

  configureKotlin()
}

internal fun Project.configureKotlinMultiplatformApplication(
  extension: KotlinMultiplatformExtension
) {
  extension.apply {
    jvmToolchain(17)

    compilerOptions {
      freeCompilerArgs.addAll(
        listOf(
          "-opt-in=kotlin.RequiresOptIn",
          "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
          "-opt-in=kotlinx.coroutines.FlowPreview",
          "-opt-in=kotlin.ExperimentalMultiplatform",
          "-Xexpect-actual-classes",
        )
      )
    }

    jvm()

    listOf(
      iosArm64(),
      iosSimulatorArm64(),
    ).forEach { iosTarget ->
      iosTarget.binaries.framework {
        baseName = "ComposeApp"
        isStatic = true
      }
    }

    applyDefaultHierarchyTemplate()

    sourceSets.apply {
      commonMain {
        dependencies {
        }
      }

      androidMain {
        dependencies {
        }
      }

      iosMain {
        dependencies {
        }
      }

      jvmMain {
        dependencies {
        }
      }
    }
  }

  configureKotlin()
}

private fun Project.configureKotlin() {
  tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_11)
      val warningsAsErrors: String? by project
      allWarningsAsErrors.set(warningsAsErrors.toBoolean())
      freeCompilerArgs.addAll(
        listOf(
          "-opt-in=kotlin.RequiresOptIn",
          "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
          "-opt-in=kotlinx.coroutines.FlowPreview",
          "-opt-in=kotlin.ExperimentalMultiplatform",
          "-Xexpect-actual-classes",
        )
      )
    }
  }
}

private fun Project.findVersion(alias: String): String {
  return libs.findVersion(alias).get().requiredVersion
}
