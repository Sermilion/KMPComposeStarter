// The DI composition root, deliberately its own module rather than part of `composeApp`.
//
// `composeApp` builds the iOS framework, and a framework exports the Objective-C API of the module
// it is built from — dependencies are not exported unless declared with `export()`. Merging the
// component here therefore keeps kotlin-inject-anvil's generated component classes out of that
// exported surface, which is what stops the Kotlin/Native backend from crashing while generating
// ObjC constructor adapters for them. See `IosApplicationComponent`.
//
// It depends on every module that contributes bindings, because merging is what it exists to do.
plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.ksp)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      // api: these types appear in AppComponent's own surface, which composeApp consumes.
      api(projects.core.common)
      api(projects.core.domain)

      // Bindings to merge. Nothing here is referenced by name; they are on the classpath so the
      // component can find their contributions.
      implementation(projects.core.data)
      implementation(projects.core.datastore)
      implementation(projects.feature.auth)
      implementation(projects.feature.home)
      implementation(projects.feature.profile)
      implementation(projects.feature.settings)
    }

    jvmMain.dependencies {
      // api: JvmApplicationComponent exposes the client so `main` can close it on shutdown.
      api(libs.ktor.client.core)
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
