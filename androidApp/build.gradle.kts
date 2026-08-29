plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kmp.kotlininject)
}

android {
  namespace = "com.sermilion.kmpcomposestarter"
  compileSdk =
    libs.versions.compileSdk
      .get()
      .toInt()

  defaultConfig {
    applicationId = "com.sermilion.kmpcomposestarter"
    minSdk =
      libs.versions.minSdk
        .get()
        .toInt()
    targetSdk =
      libs.versions.compileSdk
        .get()
        .toInt()
    versionCode = 1
    versionName = "1.0.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildFeatures {
    buildConfig = true
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }

  buildTypes {
    getByName("debug") {
      applicationIdSuffix = ".debug"
    }

    getByName("release") {
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

dependencies {
  implementation(projects.composeApp)
  implementation(projects.core.common)
  implementation(projects.core.data)
  implementation(projects.core.datastore)
  implementation(projects.core.designsystem)
  implementation(projects.core.domain)
  implementation(projects.core.ui)
  implementation(projects.feature.auth)
  implementation(projects.feature.home)
  implementation(projects.feature.profile)
  implementation(projects.feature.settings)

  implementation(
    "org.jetbrains.compose.runtime:runtime:${libs.versions.composeMultiplatform.get()}",
  )

  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.lifecycle.viewmodel)
  implementation(libs.androidx.lifecycle.runtime)
  implementation(libs.androidx.datastore.core)
  implementation(libs.kotlin.inject.runtime)
  implementation(libs.kotlin.inject.anvil.runtime)
  implementation(libs.kotlin.inject.anvil.runtime.optional)

  debugImplementation(
    "org.jetbrains.compose.ui:ui-tooling:${libs.versions.composeMultiplatform.get()}",
  )
}
