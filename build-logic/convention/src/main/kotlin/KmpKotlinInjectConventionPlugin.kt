import com.sermilion.kmpcomposestarter.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KmpKotlinInjectConventionPlugin : Plugin<Project> {

  private companion object {
    val KSP_CONFIGURATIONS = listOf(
      "kspCommonMainMetadata",
      "kspAndroid",
      "kspIosArm64",
      "kspIosSimulatorArm64",
      "kspIosX64",
      "kspJvm",
    )
  }

  override fun apply(target: Project) {
    with(target) {
      dependencies {
        "implementation"(libs.findLibrary("kotlin.inject.runtime").get())
        "implementation"(libs.findLibrary("kotlin.inject.anvil.runtime").get())
        "implementation"(libs.findLibrary("kotlin.inject.anvil.runtime.optional").get())
      }

      pluginManager.withPlugin("com.google.devtools.ksp") {
        dependencies {
          val kotlinInjectCompiler = libs.findLibrary("kotlin.inject.compiler").get()
          val anvilCompiler = libs.findLibrary("kotlin.inject.anvil.compiler").get()
          val viewModelProcessor = project(":codegen:viewmodel-inject-processor")

          KSP_CONFIGURATIONS.forEach { config ->
            add(config, kotlinInjectCompiler)
            add(config, anvilCompiler)
            add(config, viewModelProcessor)
          }
        }
      }
    }
  }
}
