import com.google.devtools.ksp.gradle.KspExtension
import com.sermilion.kmpcomposestarter.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

class KmpKotlinInjectConventionPlugin : Plugin<Project> {
  private companion object {
    const val KOTLIN_MULTIPLATFORM_PLUGIN = "org.jetbrains.kotlin.multiplatform"
    const val KOTLIN_JVM_PLUGIN = "org.jetbrains.kotlin.jvm"
    const val ANDROID_APPLICATION_PLUGIN = "com.android.application"
    const val KSP_PLUGIN = "com.google.devtools.ksp"
    const val ROOM_PLUGIN = "androidx.room3"

    /**
     * Package the ViewModel processor resolves its DI types from. It lives here rather than in the
     * processor so the processor stays project-agnostic, and here rather than in each module so no
     * module needs per-module processor configuration.
     */
    const val DI_PACKAGE_OPTION = "di.package"
    const val DI_PACKAGE = "com.sermilion.kmpcomposestarter.common.di"
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

        val viewModelProcessor = project(":codegen:viewmodel-inject-processor")
        withKspConfigurations { config ->
          dependencies {
            add(config, libs.findLibrary("kotlin.inject.compiler").get())
            add(config, libs.findLibrary("kotlin.inject.anvil.compiler").get())
            add(config, viewModelProcessor)
          }
        }

        // Room's processor has to run on every target that compiles the database, or schema
        // generation silently stops for the ones it misses. It rides the same derived
        // configuration list so a module applying the Room plugin declares no KSP wiring at all.
        pluginManager.withPlugin(ROOM_PLUGIN) {
          withKspConfigurations { config ->
            dependencies {
              add(config, libs.findLibrary("room3.compiler").get())
            }
          }
        }
      }
    }
  }

  /**
   * Runs [addProcessors] against the KSP configuration of every target the module declares, now
   * and for any target registered afterwards. Reading `targets` once would make the wiring depend
   * on the module listing its multiplatform plugin before `ksp`, and a module that lost the race
   * would silently compile with no processors at all.
   *
   * Single-target consumers (`androidApp`, and any plain JVM module a fork adds) have one `ksp`
   * configuration instead. That fallback has to be registered while the owning plugin is being
   * applied, not from `afterEvaluate`: KSP decides whether to run or skip each per-variant task
   * from the processor classpath as it stands once the variants are locked in, which happens
   * before an `afterEvaluate` registered here would fire. Adding the processors late left
   * `:androidApp:kspDebugKotlin` permanently SKIPPED, so the module compiled whatever stale
   * generated sources were still on disk instead of failing.
   *
   * The `metadata` (common) target is skipped deliberately: `kspCommonMainMetadata` used to be
   * wired up here, but its output was never added to any compilation, so the processors ran for
   * nothing.
   */
  private fun Project.withKspConfigurations(addProcessors: (String) -> Unit) {
    pluginManager.withPlugin(KOTLIN_MULTIPLATFORM_PLUGIN) {
      extensions.configure<KotlinMultiplatformExtension> {
        targets.all {
          if (platformType != KotlinPlatformType.common) {
            addProcessors("ksp${name.replaceFirstChar(Char::uppercaseChar)}")
          }
        }
      }
    }

    var singleTargetWired = false
    val wireSingleTarget = {
      if (!singleTargetWired) {
        singleTargetWired = true
        addProcessors("ksp")
      }
    }
    pluginManager.withPlugin(ANDROID_APPLICATION_PLUGIN) { wireSingleTarget() }
    pluginManager.withPlugin(KOTLIN_JVM_PLUGIN) { wireSingleTarget() }
  }
}
