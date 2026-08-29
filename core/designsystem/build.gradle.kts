plugins {
  alias(libs.plugins.kmp.library)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.compose.compiler)
}

kotlin {
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
  }
}
