import com.android.build.api.dsl.Lint
import org.gradle.kotlin.dsl.configure

plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.kmp.jacoco)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.room3)
}

room3 {
  schemaDirectory("$projectDir/schemas")
}

kotlin {
  android {
    namespace = "news.readian.notoesapp.core.data"
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
      api(projects.core.domain)
      implementation(projects.core.common)
      implementation(projects.core.datastore)

      implementation(libs.kermit)
      implementation(libs.serialization.json)
      implementation(libs.okio)
      implementation(libs.kotlin.inject.runtime)

      api(libs.ktor.client.core)
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
      implementation(libs.sqlite.jdbc)
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
}
