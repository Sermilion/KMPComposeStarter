import news.readian.notoesapp.configureKotlinMultiplatformCompose
import news.readian.notoesapp.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpComposeConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
        apply("com.android.kotlin.multiplatform.library")
        apply("org.jetbrains.compose")
        apply("org.jetbrains.kotlin.plugin.compose")
        apply("kmp.lint")
        apply("kmp.detekt")
      }

      extensions.configure<KotlinMultiplatformExtension> {
        configureKotlinMultiplatformCompose(this)
      }

      dependencies {
        add("commonMainImplementation", libs.findLibrary("compose.components.resources").get())
        add("commonMainImplementation", libs.findLibrary("kotlinx.collections.immutable").get())
        add("commonMainImplementation", libs.findLibrary("jetbrains.lifecycle.runtime.compose").get())
      }
    }
  }
}
