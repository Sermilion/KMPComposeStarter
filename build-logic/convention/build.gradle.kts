import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
}

group = "com.sermilion.kmpstarter.buildlogic"

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions {
    jvmTarget.set(JvmTarget.JVM_11)
  }
}

dependencies {
  compileOnly(libs.android.gradlePlugin)
  compileOnly(libs.kotlin.gradlePlugin)
  compileOnly(libs.ksp.gradlePlugin)
  compileOnly(libs.detekt.gradlePlugin)
  implementation("org.jetbrains.compose:compose-gradle-plugin:1.9.3")
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
    register("kmpJacoco") {
      id = "kmp.jacoco"
      implementationClass = "KmpJacocoConventionPlugin"
    }
    register("kmpLint") {
      id = "kmp.lint"
      implementationClass = "KmpLintConventionPlugin"
    }
    register("detekt") {
      id = "kmp.detekt"
      implementationClass = "DetektConventionPlugin"
    }
  }
}
