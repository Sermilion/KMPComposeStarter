import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KmpLintConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      when {
        pluginManager.hasPlugin("com.android.application") ->
          // Only the app aggregates: it is the one place where a library's lint findings actually
          // ship, and letting every library re-check its dependencies multiplies the same work.
          configure<ApplicationExtension> { lint { configure(project, checkDependencies = true) } }

        pluginManager.hasPlugin("com.android.library") ->
          configure<LibraryExtension> { lint { configure(project, checkDependencies = false) } }

        else -> {
          pluginManager.apply("com.android.lint")
          configure<Lint> { configure(project, checkDependencies = false) }
        }
      }
    }
  }
}

private fun Lint.configure(project: Project, checkDependencies: Boolean) {
  xmlReport = true
  this.checkDependencies = checkDependencies
  checkGeneratedSources = false
  val lintConfigFile = project.file("lint.xml")
  if (lintConfigFile.exists()) {
    lintConfig = lintConfigFile
  }
}
