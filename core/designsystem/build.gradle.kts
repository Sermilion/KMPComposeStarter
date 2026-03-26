plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.kmp.jacoco)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.compose.compiler)
}

kotlin {
  android {
    namespace = "news.readian.notoesapp.core.designsystem"
    compileSdk =
      libs.versions.compileSdk
        .get()
        .toInt()
    minSdk =
      libs.versions.minSdk
        .get()
        .toInt()
    withHostTestBuilder {}
    androidResources {
      enable = true
    }
  }

  sourceSets {
    commonMain.dependencies {
      api(projects.core.common)
      api(libs.compose.foundation)
      api(libs.compose.material3)
      api(libs.compose.materialIconsCore)
      api(libs.compose.runtime)
      api(libs.compose.ui)
      api(libs.compose.animation)
      api(libs.compose.components.resources)
      api(libs.kotlinx.collections.immutable)
    }

    androidMain.dependencies {
      api(libs.androidx.activity.compose)
      implementation(libs.androidx.core.ktx)
      api(libs.coil.kt.compose)
    }

    iosMain.dependencies {
    }

    jvmMain.dependencies {
    }
  }
}
