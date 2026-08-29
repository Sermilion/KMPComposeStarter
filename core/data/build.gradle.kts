import com.android.build.api.dsl.Lint
import org.gradle.kotlin.dsl.configure

plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.room3)
}

room3 {
  schemaDirectory("$projectDir/schemas")
}

kotlin {
  android {
    namespace = "com.sermilion.kmpcomposestarter.core.data"
    compileSdk =
      libs.versions.compileSdk
        .get()
        .toInt()
    minSdk =
      libs.versions.minSdk
        .get()
        .toInt()
    withHostTestBuilder {}
    lint {
      disable += "RestrictedApi"
    }
    androidResources {
      enable = true
    }
  }

  compilerOptions {
    freeCompilerArgs.add("-Xsuppress-version-warnings")
    freeCompilerArgs.add("-Xexpect-actual-classes")
  }

  sourceSets {
    commonMain.dependencies {
      implementation(projects.core.domain)
      implementation(projects.core.common)
      implementation(projects.core.datastore)

      implementation(libs.kermit)
      implementation(libs.serialization.json)
      implementation(libs.okio)
      implementation(libs.kotlin.inject.runtime)

      implementation(libs.ktor.client.core)
      implementation(libs.ktor.client.content.negotiation)
      implementation(libs.ktor.client.serialization)
      implementation(libs.ktor.client.auth)
      implementation(libs.ktor.client.logging)

      implementation(libs.room3.runtime)
      implementation(libs.sqlite.bundled)

      implementation(libs.paging.common)

      implementation(libs.ksoup)

      implementation(libs.kotlinx.datetime)
      implementation(libs.kotlinx.atomicfu)
    }

    androidMain.dependencies {
      implementation(libs.androidx.datastore)

      implementation(libs.okhttp.debug.logger)
      implementation(libs.pluto)

      implementation(libs.androidx.activity.compose)
      implementation(libs.core.ktx)

      implementation(libs.ktor.client.okhttp)

      implementation(libs.paging.runtime)
      implementation(libs.paging.compose)
    }

    iosMain.dependencies {
      implementation(libs.ktor.client.darwin)
    }

    jvmMain.dependencies {
      implementation(libs.ktor.client.okhttp)
    }

    commonTest.dependencies {
      implementation(libs.kotest.assertions.core)
      implementation(libs.kotest.framework.engine)
      implementation(libs.kotest.framework.datatest)
      implementation(libs.kotlinx.coroutines.test)
      implementation(libs.ktor.client.mock)
      implementation(kotlin("test"))
    }

    getByName("androidHostTest").dependencies {
      implementation(projects.core.testing)
      implementation(libs.androidx.junit)
      implementation(libs.kotest.runner.junit5.jvm)
      implementation(libs.mockk.android)
    }

    jvmTest.dependencies {
      implementation(libs.kotest.runner.junit5.jvm)
      implementation(libs.mockk.core)
    }
  }
}

dependencies {
  add("kspAndroid", libs.room3.compiler)
  add("kspIosArm64", libs.room3.compiler)
  add("kspIosSimulatorArm64", libs.room3.compiler)
  add("kspJvm", libs.room3.compiler)

  add("kspAndroid", libs.kotlin.inject.compiler)
  add("kspAndroid", libs.kotlin.inject.anvil.compiler)
  add("kspIosArm64", libs.kotlin.inject.compiler)
  add("kspIosArm64", libs.kotlin.inject.anvil.compiler)
  add("kspIosSimulatorArm64", libs.kotlin.inject.compiler)
  add("kspIosSimulatorArm64", libs.kotlin.inject.anvil.compiler)
  add("kspJvm", libs.kotlin.inject.compiler)
  add("kspJvm", libs.kotlin.inject.anvil.compiler)
}

configure<Lint> {
  disable += "RestrictedApi"
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
  // Keeps the desktop data directory (databases and the preferences file) inside the build
  // directory, so running tests never writes into the developer's home directory.
  systemProperty(
    "starter.dataDir",
    layout.buildDirectory
      .dir("test-data")
      .get()
      .asFile.absolutePath,
  )
}

// The base URL is a build input, not a hard-coded string: a fork points its own backend at
// `starter.api.baseUrl` in gradle.properties or on the command line. The default host is
// deliberately fake — this template ships no real backend.
val apiBaseUrl: Provider<String> =
  providers.gradleProperty("starter.api.baseUrl").orElse("https://api.example.com/")

val generateNetworkConfig by tasks.registering {
  val baseUrl = apiBaseUrl
  val outputDirectory = layout.buildDirectory.dir("generated/network/kotlin")
  inputs.property("baseUrl", baseUrl)
  outputs.dir(outputDirectory)

  doLast {
    val target = outputDirectory
      .get()
      .asFile
      .resolve("com/sermilion/kmpcomposestarter/core/data/network/NetworkConfig.kt")
    target.parentFile.mkdirs()
    target.writeText(
      """
      package com.sermilion.kmpcomposestarter.core.data.network

      /** Generated from the `starter.api.baseUrl` Gradle property. Do not edit. */
      internal object NetworkConfig {
        const val BASE_URL: String = "${baseUrl.get()}"
      }
      """.trimIndent() + "\n",
    )
  }
}

kotlin.sourceSets.commonMain {
  kotlin.srcDir(generateNetworkConfig)
}

// The Kotlin compile tasks inherit the generator dependency from the source set, but the KSP tasks
// read the same source roots through a file collection that does not always carry it, so they can
// run before `NetworkConfig.kt` exists and fail on the unresolved reference in `HttpClientProvider`.
// Declared explicitly rather than left to inference.
tasks.matching { it.name.startsWith("ksp") }.configureEach {
  dependsOn(generateNetworkConfig)
}
