plugins {
  alias(libs.plugins.kmp.application)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
}

kotlin {
  sourceSets {
    all {
      languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
      languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
    }

    androidMain.dependencies {
      implementation(compose.preview)
      implementation(libs.androidx.activity.compose)
      implementation(libs.androidx.lifecycle.viewmodel)
      implementation(libs.androidx.lifecycle.runtime)
      implementation(libs.androidx.datastore.core)
    }

    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.animation)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)

      implementation(projects.core.common)
      implementation(projects.core.ui)
      implementation(projects.core.designsystem)
      implementation(projects.core.domain)
      implementation(projects.core.datastore)
      implementation(projects.core.data)

      implementation(projects.feature.auth)
      implementation(projects.feature.home)
      implementation(projects.feature.profile)
      implementation(projects.feature.settings)

      implementation(libs.kotlinx.collections.immutable)
      implementation(libs.serialization.json)
      implementation(libs.kermit)
      implementation(libs.navigation3.ui)
      implementation(libs.lifecycle.viewmodel.navigation3)
      implementation(libs.jetbrains.lifecycle.viewmodel)
      implementation(libs.jetbrains.lifecycle.viewmodel.compose)
    }

    jvmMain.dependencies {
      implementation(compose.desktop.currentOs)
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.androidx.datastore)
    }

    jvmTest.dependencies {
      implementation(libs.kotest.framework.engine)
      implementation(libs.kotest.assertions.core)
      implementation(libs.kotest.runner.junit5.jvm)
      implementation(libs.kotlinx.coroutines.test)
    }
  }
}

tasks.named<Test>("jvmTest") {
  useJUnitPlatform()
}

dependencies {
  add("kspCommonMainMetadata", libs.kotlin.inject.compiler)
  add("kspCommonMainMetadata", libs.kotlin.inject.anvil.compiler)
  add("kspAndroid", libs.kotlin.inject.compiler)
  add("kspAndroid", libs.kotlin.inject.anvil.compiler)
  add("kspIosArm64", libs.kotlin.inject.compiler)
  add("kspIosArm64", libs.kotlin.inject.anvil.compiler)
  add("kspIosSimulatorArm64", libs.kotlin.inject.compiler)
  add("kspIosSimulatorArm64", libs.kotlin.inject.anvil.compiler)
  add("kspIosX64", libs.kotlin.inject.compiler)
  add("kspIosX64", libs.kotlin.inject.anvil.compiler)
  add("kspJvm", libs.kotlin.inject.compiler)
  add("kspJvm", libs.kotlin.inject.anvil.compiler)
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
  debugImplementation(compose.uiTooling)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
  if (name.contains("IosSimulatorArm64")) {
    dependsOn("kspKotlinIosSimulatorArm64")
  }
  if (name.contains("IosArm64") && !name.contains("Simulator")) {
    dependsOn("kspKotlinIosArm64")
  }
  if (name.contains("IosX64")) {
    dependsOn("kspKotlinIosX64")
  }
  if (name.contains("Jvm")) {
    dependsOn("kspKotlinJvm")
  }
}

kotlin.sourceSets.named("iosSimulatorArm64Main") {
  kotlin.srcDir("build/generated/ksp/iosSimulatorArm64/iosSimulatorArm64Main/kotlin")
}
kotlin.sourceSets.named("iosArm64Main") {
  kotlin.srcDir("build/generated/ksp/iosArm64/iosArm64Main/kotlin")
}
kotlin.sourceSets.named("iosX64Main") {
  kotlin.srcDir("build/generated/ksp/iosX64/iosX64Main/kotlin")
}
kotlin.sourceSets.named("jvmMain") {
  kotlin.srcDir("build/generated/ksp/jvm/jvmMain/kotlin")
}

compose.desktop {
  application {
    mainClass = "com.sermilion.kmpcomposestarter.MainKt"

    nativeDistributions {
      targetFormats(
        org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
        org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
        org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb,
      )
      packageName = "KMPComposeStarter"
      packageVersion = "1.0.0"

      modules(
        "java.sql",
        "jdk.unsupported",
      )

      includeAllModules = true
    }
  }
}
