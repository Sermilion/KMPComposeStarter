plugins {
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.spotless)
  alias(libs.plugins.org.jetbrains.kotlin.android) apply false
  alias(libs.plugins.detekt) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.compose.multiplatform) apply false
  alias(libs.plugins.sqldelight) apply false
  alias(libs.plugins.kover) apply false
}

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

buildscript {
  repositories {
    google()
    mavenCentral()
  }
}

spotless {
  predeclareDeps()
}

configure<com.diffplug.gradle.spotless.SpotlessExtensionPredeclare> {
  kotlin {
    ktlint()
  }
}

subprojects {
  apply(plugin = "com.diffplug.spotless")

  configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
      target("**/*.kt")
      targetExclude("${layout.buildDirectory}/**/*.kt")
      targetExclude("bin/**/*.kt")
      ktlint().editorConfigOverride(
        mapOf(
          "indent_size" to 2,
          "ij_kotlin_allow_trailing_comma" to true,
          "ij_kotlin_allow_trailing_comma_on_call_site" to true,
          "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
          "max_line_length" to 120,
          "ktlint_standard_no-wildcard-imports" to "disabled",
        ),
      )
    }
    kotlinGradle {
      target("*.gradle.kts")
      ktlint()
    }
  }

  afterEvaluate {
    extensions.findByType<com.android.build.gradle.BaseExtension>()?.apply {
      packagingOptions {
        resources.excludes.addAll(
          listOf(
            "/META-INF/{AL2.0,LGPL2.1}",
            "META-INF/versions/**",
            "META-INF/LICENSE.md",
            "META-INF/LICENSE-notice.md"
          )
        )
      }
    }
  }
}
