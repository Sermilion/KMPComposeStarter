import com.sermilion.kmpcomposestarter.configureKotlinMultiplatformApplication
import com.sermilion.kmpcomposestarter.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpApplicationConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.multiplatform")
        apply("org.jetbrains.compose")
        apply("org.jetbrains.kotlin.plugin.compose")
        apply("kmp.detekt")
      }

      extensions.configure<KotlinMultiplatformExtension> {
        configureKotlinMultiplatformApplication(this)
      }

      extensions.configure<ComposeExtension> {
      }

      dependencies {
        add("commonMainImplementation", libs.findLibrary("kotlinx.collections.immutable").get())
        add("commonMainImplementation", libs.findLibrary("jetbrains.lifecycle.runtime.compose").get())
        add("commonMainImplementation", libs.findLibrary("jetbrains.lifecycle.viewmodel").get())
        add("commonMainImplementation", libs.findLibrary("jetbrains.lifecycle.viewmodel.compose").get())
        add("commonMainImplementation", libs.findLibrary("navigation3.ui").get())
        add("commonMainImplementation", libs.findLibrary("lifecycle.viewmodel.navigation3").get())
      }
    }
  }
}
