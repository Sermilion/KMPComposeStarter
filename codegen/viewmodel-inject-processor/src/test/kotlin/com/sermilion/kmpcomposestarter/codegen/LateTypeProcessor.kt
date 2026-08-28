package com.sermilion.kmpcomposestarter.codegen

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated

/**
 * Emits `com.example.testapp.LateType` in the first round only. A ViewModel that references it
 * therefore cannot resolve during round one, which is exactly the situation the processor has to
 * survive by deferring the symbol instead of dropping it.
 */
class LateTypeProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
    object : SymbolProcessor {
      private var emitted = false

      override fun process(resolver: Resolver): List<KSAnnotated> {
        if (emitted) return emptyList()
        emitted = true
        environment.codeGenerator
          .createNewFile(Dependencies(aggregating = false), "com.example.testapp", "LateType")
          .bufferedWriter()
          .use { writer ->
            writer.write("package com.example.testapp\n\nclass LateType(val value: String)\n")
          }
        return emptyList()
      }
    }
}
