plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.sqldelight)
}

android {
  namespace = "com.sermilion.kmpstarter.core.data"

  testOptions {
    unitTests.all {
      it.useJUnitPlatform()
    }
  }
}

sqldelight {
  databases {
    create("UserDatabase") {
      packageName.set("com.sermilion.kmpstarter.core.data.db")
      srcDirs.setFrom("src/commonMain/sqldelight/user")
      schemaOutputDirectory.set(file("build/sqldelight/databases/user"))
      // TODO: Enable migration verification once real schema is defined
      // This should be true in production to catch migration issues at compile-time
      // verifyMigrations = true ensures that all migration files correctly transform
      // the database schema from one version to the next
      verifyMigrations.set(false)
    }
  }
}

afterEvaluate {
  tasks
    .matching {
      it.name.startsWith("ksp") &&
        (it.name.contains("Android") || it.name.contains("Ios") || it.name.contains("Jvm")) &&
        !it.name.contains("Test")
    }.configureEach {
      dependsOn("generateCommonMainUserDatabaseInterface")
    }
}

kotlin {
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

      implementation(libs.sqldelight.coroutines.extensions)
      implementation(libs.sqldelight.primitive.adapters)

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

      implementation(libs.sqldelight.android.driver)
      implementation(libs.sqldelight.paging3.extensions)

      implementation(libs.paging.runtime)
      implementation(libs.paging.compose)
    }

    iosMain.dependencies {
      implementation(libs.sqldelight.native.driver)
      implementation(libs.ktor.client.darwin)
    }

    jvmMain.dependencies {
      implementation(libs.sqldelight.jdbc.driver)
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

    androidUnitTest.dependencies {
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
  add("kspAndroid", libs.kotlin.inject.compiler)
  add("kspIosArm64", libs.kotlin.inject.compiler)
  add("kspIosSimulatorArm64", libs.kotlin.inject.compiler)
  add("kspIosX64", libs.kotlin.inject.compiler)
  add("kspJvm", libs.kotlin.inject.compiler)
}

tasks.named<Test>("jvmTest") {
  useJUnitPlatform()
}
