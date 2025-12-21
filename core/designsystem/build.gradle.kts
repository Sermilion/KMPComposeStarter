plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.kmp.jacoco)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.compose.compiler)
}

android {
  namespace = "com.sermilion.kmpstarter.core.designsystem"
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(projects.core.common)
      api(compose.foundation)
      api(compose.material3)
      api(compose.materialIconsExtended)
      api(compose.runtime)
      api(compose.ui)
      api(compose.animation)
      api(compose.components.resources)
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
