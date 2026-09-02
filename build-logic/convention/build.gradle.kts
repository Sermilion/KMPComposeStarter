import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
  alias(libs.plugins.detekt)
  alias(libs.plugins.spotless)
}

group = "com.sermilion.kmpcomposestarter.buildlogic"

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions {
    jvmTarget.set(JvmTarget.JVM_17)
  }
}

dependencies {
  compileOnly(libs.android.gradlePlugin)
  compileOnly(libs.kotlin.gradlePlugin)
  compileOnly(libs.ksp.gradlePlugin)
  compileOnly(libs.detekt.gradlePlugin)
  implementation(libs.compose.gradle.plugin)
  // The shared config declares the Compose ruleset, and detekt's `config.validation` rejects a
  // ruleset it cannot resolve, so the rules have to be on this build's detekt classpath too.
  detektPlugins(libs.detekt.compose.rules)
}

detekt {
  // build-logic is its own included build, so the shared config lives one directory up.
  config.setFrom(rootDir.parentFile.resolve("config/detekt/detekt.yml"))
  basePath = rootDir.parentFile.absolutePath
  buildUponDefaultConfig = true
}

spotless {
  kotlin {
    target("src/**/*.kt")
    ktlint()
  }
  kotlinGradle {
    target("*.gradle.kts")
    ktlint()
  }
}

tasks {
  validatePlugins {
    enableStricterValidation = true
    failOnWarning = true
  }
}

gradlePlugin {
  plugins {
    register("kmpLibrary") {
      id = "kmp.library"
      implementationClass = "KmpLibraryConventionPlugin"
    }
    register("kmpCompose") {
      id = "kmp.compose"
      implementationClass = "KmpComposeConventionPlugin"
    }
    register("kmpApplication") {
      id = "kmp.application"
      implementationClass = "KmpApplicationConventionPlugin"
    }
    register("jvmLibrary") {
      id = "jvm.library"
      implementationClass = "JvmLibraryConventionPlugin"
    }
    register("kmpLint") {
      id = "kmp.lint"
      implementationClass = "KmpLintConventionPlugin"
    }
    register("detekt") {
      id = "kmp.detekt"
      implementationClass = "DetektConventionPlugin"
    }
    register("kmpKotlinInject") {
      id = "kmp.kotlininject"
      implementationClass = "KmpKotlinInjectConventionPlugin"
    }
  }
}
