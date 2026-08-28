package com.sermilion.kmpcomposestarter.codegen

import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.configureKsp
import com.tschuchort.compiletesting.sourcesGeneratedBySymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

const val TEST_DI_PACKAGE: String = "com.example.testapp.di"

/**
 * The project types the processor resolves through the `di.package` option, plus the lifecycle
 * types it recognises by name. Declared as sources so the suite stays hermetic: it exercises the
 * processor, not artifact resolution.
 */
fun diSupportSources(diPackage: String): List<SourceFile> = listOf(
  SourceFile.kotlin(
    "Lifecycle.kt",
    """
    package androidx.lifecycle

    abstract class ViewModel

    class SavedStateHandle
    """.trimIndent(),
  ),
  SourceFile.kotlin(
    "DiSupport.kt",
    """
    package $diPackage

    import androidx.lifecycle.ViewModel
    import kotlin.reflect.KClass

    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.BINARY)
    annotation class ContributesViewModel(val scope: KClass<*>)

    interface AssistedArgs {
      operator fun <T> get(name: String): T?
    }

    interface ViewModelEntry {
      val kclass: KClass<out ViewModel>
      val savedStateHandleArgName: String?
      fun create(args: AssistedArgs): ViewModel
    }

    annotation class UserScope

    annotation class ScreenScope
    """.trimIndent(),
  ),
)

@OptIn(ExperimentalCompilerApi::class)
fun compileWithProcessor(
  vararg sources: SourceFile,
  diPackage: String = TEST_DI_PACKAGE,
  processorOptions: Map<String, String> = mapOf("di.package" to diPackage),
  extraProcessors: List<SymbolProcessorProvider> = emptyList(),
): JvmCompilationResult = KotlinCompilation()
  .apply {
    this.sources = diSupportSources(diPackage) + sources
    inheritClassPath = true
    messageOutputStream = System.out
    configureKsp {
      symbolProcessorProviders += ViewModelInjectProcessorProvider()
      symbolProcessorProviders += extraProcessors
      this.processorOptions += processorOptions
    }
  }
  .compile()

@OptIn(ExperimentalCompilerApi::class)
fun JvmCompilationResult.generatedSourceOrNull(fileName: String): String? =
  sourcesGeneratedBySymbolProcessor.firstOrNull { it.name == fileName }?.readText()

@OptIn(ExperimentalCompilerApi::class)
fun JvmCompilationResult.generatedSource(fileName: String): String =
  requireNotNull(generatedSourceOrNull(fileName)) {
    "No generated file named $fileName. Generated: " +
      sourcesGeneratedBySymbolProcessor.joinToString { it.name }
  }
