plugins {
  alias(libs.plugins.kotlin.jvm)
}

dependencies {
  implementation(libs.ksp.symbol.processing.api)
  implementation(libs.kotlin.inject.runtime)
  implementation(libs.kotlin.inject.anvil.runtime)
  implementation(libs.kotlinpoet)
  implementation(libs.kotlinpoet.ksp)

  testImplementation(libs.kotlin.compile.testing)
  testImplementation(libs.kotlin.compile.testing.ksp)
  testImplementation(libs.kotest.framework.engine)
  testImplementation(libs.kotest.assertions.core)
  testImplementation(libs.kotest.runner.junit5.jvm)
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}
