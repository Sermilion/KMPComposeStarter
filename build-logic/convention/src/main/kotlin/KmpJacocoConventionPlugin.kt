import com.android.build.api.variant.AndroidComponentsExtension
import news.readian.notoesapp.configureJacoco
import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpJacocoConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("org.gradle.jacoco")
      }

      val androidComponentsExtension =
        extensions.findByType(AndroidComponentsExtension::class.java)
      if (androidComponentsExtension != null) {
        configureJacoco(androidComponentsExtension)
      }
    }
  }
}
