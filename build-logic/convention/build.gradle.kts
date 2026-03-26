import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
}

group = "news.readian.notoesapp.buildlogic"

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
    register("kmpKotlinInject") {
      id = "kmp.kotlininject"
      implementationClass = "KmpKotlinInjectConventionPlugin"
    }
  }
}
