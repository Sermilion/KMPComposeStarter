plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.kmp.jacoco)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.sermilion.kmpstarter.core.datastore"

  testOptions {
    unitTests.all {
      it.useJUnitPlatform()
    }
  }
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.core.domain)
      implementation(projects.core.common)
      implementation(libs.androidx.datastore.core)
      implementation(libs.androidx.datastore.core.okio)
      implementation(libs.serialization.json)
      implementation(libs.kermit)
      implementation(libs.kotlin.inject.runtime)
      implementation(libs.okio)
    }

    androidMain.dependencies {
      implementation(libs.kotlinx.coroutines.android)
    }

    commonTest.dependencies {
      implementation(libs.kotest.assertions.core)
      implementation(libs.kotest.framework.engine)
      implementation(libs.kotest.framework.datatest)
      implementation(libs.kotlinx.coroutines.test)
      implementation(kotlin("test"))
    }

    androidUnitTest.dependencies {
      implementation(projects.core.testing)
      implementation(libs.androidx.junit)
      implementation(libs.kotest.runner.junit5.jvm)
    }

    jvmTest.dependencies {
      implementation(libs.kotest.runner.junit5.jvm)
    }
  }
}

dependencies {
  add("kspAndroid", libs.kotlin.inject.compiler)
  add("kspIosArm64", libs.kotlin.inject.compiler)
  add("kspIosSimulatorArm64", libs.kotlin.inject.compiler)
  add("kspIosX64", libs.kotlin.inject.compiler)
  add("kspJvm", libs.kotlin.inject.compiler)
}

tasks.named<Test>("jvmTest") {
  useJUnitPlatform()
}
