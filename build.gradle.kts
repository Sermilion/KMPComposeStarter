plugins {
  base
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.kmp.library) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.spotless)
  alias(libs.plugins.detekt) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.compose.multiplatform) apply false
  alias(libs.plugins.room3) apply false
  alias(libs.plugins.kover)
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
  apply(plugin = "org.jetbrains.kotlinx.kover")

  configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
      target("**/*.kt")
      // Two targetExclude calls replace each other rather than adding up, which is how the build
      // directory pattern used to go missing. Shared style lives in .editorconfig, which ktlint
      // reads directly, so there is no override map to drift from it here.
      //
      // Container projects (`:core`, `:feature`, `:codegen`) would otherwise match their children's
      // sources through `**/*.kt` as well, leaving two spotlessKotlinApply tasks writing the same
      // file. Under spotless 8 that race truncates the file instead of writing it twice.
      targetExclude(
        "**/build/**/*.kt",
        "bin/**/*.kt",
        *childProjects.values.map { "${it.projectDir.name}/**/*.kt" }.toTypedArray(),
      )
      ktlint()
    }
    kotlinGradle {
      target("*.gradle.kts")
      ktlint()
    }
  }
}

// Coverage is aggregated here so `check` produces one merged report instead of per-module
// fragments. build-logic is a separate included build and is deliberately outside this.
dependencies {
  subprojects.forEach { kover(it) }
}

kover {
  reports {
    filters {
      excludes {
        // None of this is hand-written, so counting it says nothing about what is tested. Left
        // unfiltered the merged report read 31% while the repositories, ViewModels, navigator and
        // KSP processor were all between 78% and 96%.
        classes(
          // Compose resource accessors (`Res`, `Res.string`, ...), one class per module.
          "*.generated.resources.*",
          // kotlin-inject-anvil's contribution lookup package and the merged component impls.
          "amazon.lastmile.inject.*",
          "*.Inject*Component",
          // ViewModelEntry multibindings emitted by codegen:viewmodel-inject-processor.
          "*_Entry",
          // Room's generated database, DAO and constructor implementations.
          "*_Impl",
          "*UserDatabaseConstructor*",
        )
        packages(
          // Colour, type and shape tokens: declarations with no behaviour to exercise.
          "com.sermilion.kmpcomposestarter.core.designsystem.theme",
        )
      }
    }
    total {
      xml {
        onCheck.set(true)
      }
      html {
        onCheck.set(true)
      }
    }
  }
}

// build-logic is an included build, so its detekt and spotless tasks are invisible to the root
// gate unless they are wired in explicitly.
tasks.named("check") {
  dependsOn(gradle.includedBuild("build-logic").task(":convention:check"))
}
