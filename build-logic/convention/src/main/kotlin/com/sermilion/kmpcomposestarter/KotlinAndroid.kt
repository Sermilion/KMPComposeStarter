package com.sermilion.kmpcomposestarter

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
  commonExtension: ApplicationExtension,
) {
  commonExtension.configureAndroidDefaults(this)

  configureKotlin()

  dependencies {
    add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
  }
}

internal fun Project.configureKotlinAndroid(
  commonExtension: LibraryExtension,
) {
  commonExtension.configureAndroidDefaults(this)

  configureKotlin()

  dependencies {
    add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
  }
}

private fun Project.findVersion(alias: String): String {
  return libs.findVersion(alias).get().requiredVersion
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
        )
      )
    }
  }
}

private fun ApplicationExtension.configureAndroidDefaults(project: Project) {
  compileSdk = project.findVersion("compileSdk").toInt()

  defaultConfig {
    minSdk = project.findVersion("minSdk").toInt()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    isCoreLibraryDesugaringEnabled = true
  }
}

private fun LibraryExtension.configureAndroidDefaults(project: Project) {
  compileSdk = project.findVersion("compileSdk").toInt()

  defaultConfig {
    minSdk = project.findVersion("minSdk").toInt()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    isCoreLibraryDesugaringEnabled = true
  }
}
