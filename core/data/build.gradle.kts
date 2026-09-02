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
  compilerOptions {
    // Room 3 alpha01 generates code that trips Kotlin's version warnings. Module-scoped on
    // purpose: this is a Room-alpha workaround, not shared build policy. Drop it with the pin.
    freeCompilerArgs.add("-Xsuppress-version-warnings")
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

      implementation(libs.kotlinx.datetime)
      implementation(libs.kotlinx.atomicfu)
    }

    androidMain.dependencies {
      implementation(libs.androidx.datastore)
      implementation(libs.androidx.activity.compose)
      implementation(libs.ktor.client.okhttp)
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

    // commonTest specs are inherited by every target's test source set, so each JVM-backed target
    // needs the JUnit Platform runner Kotest discovers through. The native targets use Kotest's
    // own engine, which commonTest already brings in.
    jvmTest.dependencies {
      implementation(projects.core.testing)
      implementation(libs.kotest.runner.junit5.jvm)
      implementation(libs.mockk.core)
    }

    getByName("androidHostTest").dependencies {
      implementation(libs.kotest.runner.junit5.jvm)
    }
  }
}

// The single RestrictedApi suppression for this module. Room 3 alpha01 reports a false positive on
// the hand-written `RoomDatabase` supertype in `UserDatabase.kt` under `lintAndroidMain`, which no
// path-scoped ignore can narrow because the file is ours. The much larger generated-KSP half of
// the same problem is handled by path in lint.xml instead — that is what `lintJvm` reads, and it
// keeps the rule live on every hand-written source in the module. Revisit both when the Room pin
// moves off alpha.
//
// This has to sit on the Android KMP library extension: that is the one `lintAndroidMain` reads.
// Setting it on the standalone `com.android.lint` extension configured nothing.
kotlin {
  androidLibrary {
    lint {
      disable += "RestrictedApi"
    }
  }
}

tasks.withType<Test>().configureEach {
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
    val target =
      outputDirectory
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
// run before `NetworkConfig.kt` exists and fail on the unresolved reference in
// `HttpClientProvider`.
// Declared explicitly rather than left to inference.
tasks.matching { it.name.startsWith("ksp") }.configureEach {
  dependsOn(generateNetworkConfig)
}
