import com.google.devtools.ksp.gradle.KspExtension
import com.sermilion.kmpcomposestarter.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class KmpKotlinInjectConventionPlugin : Plugin<Project> {

  private companion object {
    const val KOTLIN_MULTIPLATFORM_PLUGIN = "org.jetbrains.kotlin.multiplatform"
    const val KSP_PLUGIN = "com.google.devtools.ksp"

    /**
     * Package the ViewModel processor resolves its DI types from. It lives here rather than in the
     * processor so the processor stays project-agnostic, and here rather than in each module so no
     * module needs per-module processor configuration.
     */
    const val DI_PACKAGE_OPTION = "di.package"
    const val DI_PACKAGE = "com.sermilion.kmpcomposestarter.common.di"

    val KMP_KSP_CONFIGURATIONS = listOf(
      "kspCommonMainMetadata",
      "kspAndroid",
      "kspIosArm64",
      "kspIosSimulatorArm64",
      "kspJvm",
    )
    val JVM_KSP_CONFIGURATIONS = listOf("ksp")
  }

  override fun apply(target: Project) {
    with(target) {
      pluginManager.withPlugin(KOTLIN_MULTIPLATFORM_PLUGIN) {
        dependencies {
          add("commonMainImplementation", libs.findLibrary("kotlin.inject.runtime").get())
          add("commonMainImplementation", libs.findLibrary("kotlin.inject.anvil.runtime").get())
          add(
            "commonMainImplementation",
            libs.findLibrary("kotlin.inject.anvil.runtime.optional").get(),
          )
        }
      }

      pluginManager.withPlugin(KSP_PLUGIN) {
        extensions.configure<KspExtension> {
          arg(DI_PACKAGE_OPTION, DI_PACKAGE)
        }

        val kspConfigurations = if (pluginManager.hasPlugin(KOTLIN_MULTIPLATFORM_PLUGIN)) {
          KMP_KSP_CONFIGURATIONS
        } else {
          JVM_KSP_CONFIGURATIONS
        }

        dependencies {
          val kotlinInjectCompiler = libs.findLibrary("kotlin.inject.compiler").get()
          val anvilCompiler = libs.findLibrary("kotlin.inject.anvil.compiler").get()
          val viewModelProcessor = project(":codegen:viewmodel-inject-processor")

          kspConfigurations.forEach { config ->
            add(config, kotlinInjectCompiler)
            add(config, anvilCompiler)
            add(config, viewModelProcessor)
          }
        }
      }
    }
  }
}
