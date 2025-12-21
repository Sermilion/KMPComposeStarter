package com.sermilion.kmpcomposestarter.codegen

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class ViewModelInjectProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
    ViewModelInjectProcessor(
      codeGenerator = environment.codeGenerator,
      logger = environment.logger,
    )
}
