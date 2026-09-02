import com.sermilion.kmpcomposestarter.configureJvmBytecodeTarget
import com.sermilion.kmpcomposestarter.configureTests
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure

/**
 * Plain JVM modules (today only `codegen:viewmodel-inject-processor`) get the same static analysis,
 * bytecode baseline and test-platform wiring as the multiplatform modules. Android lint is
 * deliberately not applied: there is no Android code here for it to check.
 */
class JvmLibraryConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("org.jetbrains.kotlin.jvm")
        apply("kmp.detekt")
      }

      // `org.jetbrains.kotlin.jvm` brings the java plugin with it, and Kotlin rejects a
      // `compileJava`/`compileKotlin` target mismatch, so javac has to move to 11 as well.
      extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
      }

      configureJvmBytecodeTarget()
      configureTests()
    }
  }
}
