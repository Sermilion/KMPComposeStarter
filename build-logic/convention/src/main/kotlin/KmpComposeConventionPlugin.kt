import com.sermilion.kmpcomposestarter.configureKotlinMultiplatformCompose
import com.sermilion.kmpcomposestarter.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpComposeConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.library")
        apply("org.jetbrains.kotlin.multiplatform")
        apply("org.jetbrains.compose")
        apply("org.jetbrains.kotlin.plugin.compose")
        apply("kmp.lint")
        apply("kmp.detekt")
      }

      extensions.configure<KotlinMultiplatformExtension> {
        configureKotlinMultiplatformCompose(this)
      }

      val composeExtension = extensions.getByType<ComposeExtension>()
      val compose = composeExtension.dependencies

      dependencies {
        add("commonMainImplementation", compose.components.resources)
        add("commonMainImplementation", libs.findLibrary("kotlinx.collections.immutable").get())
        add("commonMainImplementation", libs.findLibrary("jetbrains.lifecycle.runtime.compose").get())
      }
    }
  }
}
