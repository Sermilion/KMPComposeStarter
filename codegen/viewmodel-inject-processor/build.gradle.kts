plugins {
  alias(libs.plugins.kotlin.jvm)
}

dependencies {
  implementation(libs.ksp.symbol.processing.api)
  implementation(libs.kotlin.inject.runtime)
  implementation(libs.kotlin.inject.anvil.runtime)
  implementation(libs.kotlinpoet)
  implementation(libs.kotlinpoet.ksp)
}
