package com.sermilion.kmpcomposestarter.codegen

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class ViewModelInjectProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    val diPackage = environment.options[DI_PACKAGE_OPTION]
    require(!diPackage.isNullOrBlank()) {
      "Missing KSP option '$DI_PACKAGE_OPTION'. It names the package holding " +
        "ContributesViewModel, ViewModelEntry, AssistedArgs, UserScope and ScreenScope. " +
        "The kmp.kotlininject convention plugin sets it for every module."
    }
    return ViewModelInjectProcessor(
      codeGenerator = environment.codeGenerator,
      logger = environment.logger,
      diPackage = diPackage,
    )
  }

  companion object {
    const val DI_PACKAGE_OPTION = "di.package"
  }
}
