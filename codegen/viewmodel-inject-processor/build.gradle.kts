plugins {
  alias(libs.plugins.jvm.library)
}

dependencies {
  implementation(libs.ksp.symbol.processing.api)
  implementation(libs.kotlin.inject.runtime)
  implementation(libs.kotlin.inject.anvil.runtime)
  implementation(libs.kotlinpoet)
  implementation(libs.kotlinpoet.ksp)

  // The compiled test sources reference the real AppScope, which ships in runtime-optional;
  // the processor itself only ever names it as a string, so this is a test-only dependency.
  testImplementation(libs.kotlin.inject.anvil.runtime.optional)
  testImplementation(libs.kotlin.compile.testing)
  testImplementation(libs.kotlin.compile.testing.ksp)
  testImplementation(libs.kotest.framework.engine)
  testImplementation(libs.kotest.assertions.core)
  testImplementation(libs.kotest.runner.junit5.jvm)
}
