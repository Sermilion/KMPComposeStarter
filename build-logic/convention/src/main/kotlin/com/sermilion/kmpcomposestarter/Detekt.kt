package com.sermilion.kmpcomposestarter

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.gradle.language.base.plugins.LifecycleBasePlugin
import java.io.File

internal fun Project.configureDetekt() {
  val reportMerge: TaskProvider<ReportMergeTask> =
    rootProject.registerMaybe("detektReportMerge") {
      description = "Runs merge of all detekt reports into single one"
      output.set(rootProject.layout.buildDirectory.file("reports/detekt/merged.xml"))
    }

  configure<DetektExtension> {
    // The shared config is always loaded; a module-level detekt.yml only layers on top of it.
    // Loading it only when a module config existed is what left config/detekt/detekt.yml dead
    // across the whole repository, with detekt silently running on its defaults instead.
    val moduleConfig = file("detekt.yml")
    config.setFrom(
      listOfNotNull(
        rootProject.file("config/detekt/detekt.yml"),
        moduleConfig.takeIf(File::exists),
      ),
    )
    basePath = rootDir.absolutePath
    buildUponDefaultConfig = true
    ignoredBuildTypes = listOf("release")
  }

  tasks.withType<Detekt>().configureEach {
    jvmTarget = JavaVersion.VERSION_11.toString()
    exclude {
      // Separator-safe: absolutePath uses `\` on Windows, so the literal "/build/" checks that
      // used to live here matched nothing there and analysed generated code instead.
      val path = it.file.invariantSeparatorsPath
      path.contains("/build/") || path.contains("/generated/")
    }
    reports {
      html.required.set(true)
      xml.required.set(true)
    }
    finalizedBy(reportMerge)
    reportMerge.configure {
      input.from(xmlReportFile)
    }
  }

  // `check` used to reach only the `detekt` task, so androidMain, jvmMain, both iOS targets and
  // every test source set went unanalysed. Depend on all of them instead.
  plugins.withType<LifecycleBasePlugin> {
    tasks.named(LifecycleBasePlugin.CHECK_TASK_NAME).configure {
      dependsOn(tasks.withType<Detekt>())
    }
  }
}
