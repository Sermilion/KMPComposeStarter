package com.sermilion.kmpcomposestarter

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeSimulatorTest
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Opt-ins and language flags shared by every module. They are declared once, on the multiplatform
 * extension, because that is the only level that also reaches the native compilations — setting
 * them on [KotlinCompile] would silently skip the iOS targets.
 */
private val SHARED_COMPILER_ARGS =
  listOf(
    "-opt-in=kotlin.RequiresOptIn",
    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
    "-opt-in=kotlinx.coroutines.FlowPreview",
    "-opt-in=kotlin.ExperimentalMultiplatform",
    "-Xexpect-actual-classes",
  )

/** Opt-ins that only make sense where the Compose Multiplatform UI dependencies are present. */
private val COMPOSE_COMPILER_ARGS =
  listOf(
    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
  )

/**
 * Root package the Android namespaces are derived from. Deriving them keeps the Android target
 * declared in exactly one place: `:core:data` becomes `com.sermilion.kmpcomposestarter.core.data`,
 * so a module's build file carries no `android { }` block at all. `androidApp` is the exception —
 * it applies `com.android.application`, whose `android { }` block also carries the applicationId,
 * build types and packaging rules that cannot be shared, so it sets its own namespace.
 */
private const val ANDROID_NAMESPACE_PREFIX = "com.sermilion.kmpcomposestarter"

/**
 * The JDK the build itself compiles against. Kept separate from the Android and JVM bytecode
 * targets, which stay on Java 11 so the template keeps its lower runtime baseline.
 */
private const val BUILD_JVM_TOOLCHAIN = 17

internal fun Project.configureKotlinMultiplatform(extension: KotlinMultiplatformExtension) {
  configureShared(extension)

  extension.jvm()
  extension.iosArm64()
  extension.iosSimulatorArm64()
  extension.applyDefaultHierarchyTemplate()
}

internal fun Project.configureKotlinMultiplatformCompose(extension: KotlinMultiplatformExtension) {
  configureKotlinMultiplatform(extension)
  extension.compilerOptions {
    freeCompilerArgs.addAll(COMPOSE_COMPILER_ARGS)
  }
}

internal fun Project.configureKotlinMultiplatformApplication(
  extension: KotlinMultiplatformExtension,
) {
  configureShared(extension)

  extension.jvm()
  listOf(
    extension.iosArm64(),
    extension.iosSimulatorArm64(),
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = "ComposeApp"
      isStatic = true
    }
  }
  extension.applyDefaultHierarchyTemplate()

  extension.compilerOptions {
    freeCompilerArgs.addAll(COMPOSE_COMPILER_ARGS)
  }
}

/** The single Android target declaration plus the Kotlin settings every KMP module inherits. */
private fun Project.configureShared(extension: KotlinMultiplatformExtension) {
  extension.jvmToolchain(BUILD_JVM_TOOLCHAIN)

  extension.compilerOptions {
    freeCompilerArgs.addAll(SHARED_COMPILER_ARGS)
  }

  extension.extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
    namespace = androidNamespace
    compileSdk =
      libs
        .findVersion("compileSdk")
        .get()
        .requiredVersion
        .toInt()
    minSdk =
      libs
        .findVersion("minSdk")
        .get()
        .requiredVersion
        .toInt()
    withHostTestBuilder {}
    androidResources {
      enable = true
    }
  }

  configureJvmBytecodeTarget()
  configureTests()
}

private val Project.androidNamespace: String
  get() =
    (
      listOf(ANDROID_NAMESPACE_PREFIX) +
        path.split(":").filter(String::isNotEmpty).map(String::toPackageSegment)
    ).joinToString(".")

/**
 * A Gradle path segment is not automatically a Java identifier: `:feature:user-profile` would
 * otherwise derive the invalid package `...feature.user-profile` and fail in AGP. Anything that
 * cannot appear in an identifier becomes `_`, and a leading digit gets one prepended.
 */
private fun String.toPackageSegment(): String {
  val sanitised = lowercase().map { if (it.isLetterOrDigit()) it else '_' }.joinToString("")
  return if (sanitised.first().isDigit()) "_$sanitised" else sanitised
}

/**
 * The Java 11 bytecode baseline, applied to every module that compiles Kotlin for the JVM —
 * multiplatform and plain JVM alike.
 */
internal fun Project.configureJvmBytecodeTarget() {
  val warningsAsErrors: String? by project
  tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_11)
      allWarningsAsErrors.set(warningsAsErrors.toBoolean())
    }
  }
}

/**
 * Kotest specs are discovered through the JUnit Platform. Declared here so no module has to repeat
 * it, and applied to every `Test` task so `androidHostTest` and `jvmTest` are both covered.
 */
internal fun Project.configureTests() {
  tasks.withType<Test>().configureEach {
    useJUnitPlatform()
  }
  configureSimulatorTests()
}

/**
 * Runs the iOS simulator tests only where a simulator toolchain actually exists.
 *
 * `commonTest` specs are inherited by the native targets, so `check` gains an
 * `iosSimulatorArm64Test` task on any macOS host. Running one needs full Xcode: a machine with
 * only the Command Line Tools installed has no `simctl`, and the task fails while merely computing
 * its `device` property — `xcrun` exits 72 before a single test runs. That made `./gradlew check`,
 * this repository's whole quality gate, unrunnable for anyone not set up for iOS development, on
 * every module, including pure-Android work.
 *
 * The tasks are therefore skipped rather than failed when no simulator is available. They are not
 * silently dropped in CI: the macOS job invokes them by name, on a runner that has Xcode, so the
 * claim that the shared specs run on iOS stays checked. Gradle reports each skip as SKIPPED.
 */
private fun Project.configureSimulatorTests() {
  val simulatorAvailable: Provider<Boolean> =
    providers
      .exec {
        commandLine("/usr/bin/xcrun", "--find", "simctl")
        isIgnoreExitValue = true
      }.result
      .map { it.exitValue == 0 }
      .orElse(false)

  tasks.withType<KotlinNativeSimulatorTest>().configureEach {
    onlyIf("an iOS simulator toolchain is available") {
      // Only macOS ever registers these tasks, so `xcrun` is present; what varies is whether the
      // active developer directory is a full Xcode or just the Command Line Tools.
      simulatorAvailable.get()
    }
  }
}
