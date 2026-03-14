package com.sermilion.kmpcomposestarter

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureAndroidCompose(
  commonExtension: ApplicationExtension,
) {
  commonExtension.configureComposeOptions(this)

  dependencies {
    val bom = libs.findLibrary("androidx-compose-bom").get()
    add("implementation", platform(bom))
    add("androidTestImplementation", platform(bom))
    add("debugImplementation", libs.findLibrary("androidx.compose.ui.testManifest").get())
    add("testImplementation", libs.findLibrary("robolectric").get())
  }

  tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
      freeCompilerArgs.addAll(buildComposeMetricsParameters())
    }
  }
}

internal fun Project.configureAndroidCompose(
  commonExtension: LibraryExtension,
) {
  commonExtension.configureComposeOptions(this)

  dependencies {
    val bom = libs.findLibrary("androidx-compose-bom").get()
    add("implementation", platform(bom))
    add("androidTestImplementation", platform(bom))
    add("debugImplementation", libs.findLibrary("androidx.compose.ui.testManifest").get())
    add("testImplementation", libs.findLibrary("robolectric").get())
  }

  tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
      freeCompilerArgs.addAll(buildComposeMetricsParameters())
    }
  }
}

private fun ApplicationExtension.configureComposeOptions(project: Project) {
  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = project.libs.findVersion("androidxComposeCompiler").get().toString()
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

private fun LibraryExtension.configureComposeOptions(project: Project) {
  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = project.libs.findVersion("androidxComposeCompiler").get().toString()
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

private fun Project.buildComposeMetricsParameters(): List<String> {
  val metricParameters = mutableListOf<String>()
  val enableMetricsProvider = project.providers.gradleProperty("enableComposeCompilerMetrics")
  val relativePath = projectDir.relativeTo(rootDir)
  val buildDir = layout.buildDirectory.get().asFile
  val enableMetrics = (enableMetricsProvider.orNull == "true")
  if (enableMetrics) {
    val metricsFolder = buildDir.resolve("compose-metrics").resolve(relativePath)
    metricParameters.add("-P")
    metricParameters.add(
      "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + metricsFolder.absolutePath
    )
  }

  val enableReportsProvider = project.providers.gradleProperty("enableComposeCompilerReports")
  val enableReports = (enableReportsProvider.orNull == "true")
  if (enableReports) {
    val reportsFolder = buildDir.resolve("compose-reports").resolve(relativePath)
    metricParameters.add("-P")
    metricParameters.add(
      "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + reportsFolder.absolutePath
    )
  }
  return metricParameters.toList()
}
