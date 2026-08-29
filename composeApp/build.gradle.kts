plugins {
  alias(libs.plugins.kmp.application)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(libs.compose.runtime)
      implementation(libs.compose.foundation)
      implementation(libs.compose.material3)
      implementation(libs.compose.ui)
      implementation(libs.compose.animation)
      implementation(libs.compose.components.resources)
      implementation(libs.compose.components.uiToolingPreview)

      implementation(projects.core.common)
      implementation(projects.core.ui)
      implementation(projects.core.designsystem)
      implementation(projects.core.domain)
      implementation(projects.core.datastore)
      implementation(projects.core.data)

      implementation(projects.feature.auth)
      implementation(projects.feature.home)
      implementation(projects.feature.profile)
      implementation(projects.feature.settings)

      implementation(libs.kotlinx.collections.immutable)
      implementation(libs.serialization.json)
      implementation(libs.kermit)
      implementation(libs.navigation3.ui)
      implementation(libs.lifecycle.viewmodel.navigation3)
      implementation(libs.navigationevent.compose)
      implementation(libs.androidx.savedstate)
      implementation(libs.androidx.savedstate.compose)
      implementation(libs.jetbrains.lifecycle.viewmodel)
      implementation(libs.jetbrains.lifecycle.viewmodel.compose)
      implementation(libs.jetbrains.lifecycle.runtime.compose)
    }

    jvmMain.dependencies {
      implementation(compose.desktop.currentOs)
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.androidx.datastore)
      implementation(libs.ktor.client.core)
    }

    jvmTest.dependencies {
      implementation(libs.kotest.framework.engine)
      implementation(libs.kotest.assertions.core)
      implementation(libs.kotest.runner.junit5.jvm)
      implementation(libs.kotlinx.coroutines.test)
    }
  }

  // Both iOS targets share one actual instead of two byte-identical copies.
  sourceSets {
    named("iosArm64Main") { kotlin.srcDir("src/iosTargetMain/kotlin") }
    named("iosSimulatorArm64Main") { kotlin.srcDir("src/iosTargetMain/kotlin") }
  }
}

compose.desktop {
  application {
    mainClass = "com.sermilion.kmpcomposestarter.MainKt"

    nativeDistributions {
      targetFormats(
        org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
        org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
        org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb,
      )
      packageName = "KMPComposeStarter"
      packageVersion = "1.0.0"

      // Only the JDK modules the desktop app needs are bundled: `java.sql` for the JVM SQLite and
      // Room stack, `jdk.unsupported` for the `sun.misc.Unsafe` access several Kotlin and Compose
      // dependencies still rely on. `includeAllModules = true` shipped the entire JDK instead,
      // which is what made the distributables large enough to be worth curating.
      modules(
        "java.sql",
        "jdk.unsupported",
      )
    }
  }
}
