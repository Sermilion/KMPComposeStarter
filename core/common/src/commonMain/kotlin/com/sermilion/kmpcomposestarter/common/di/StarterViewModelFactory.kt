package com.sermilion.kmpcomposestarter.common.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.reflect.KClass

/**
 * The single ViewModel factory in the app. One instance exists per DI scope; the scope decides
 * which [ViewModelEntry] multibindings are visible, so the same class serves the app scope
 * (pre-auth screens) and every screen scope.
 */
class StarterViewModelFactory(
  entries: Set<ViewModelEntry>,
) : ViewModelProvider.Factory {
  private val entriesByClass: Map<KClass<out ViewModel>, ViewModelEntry> =
    entries.associateBy { it.kclass }.also { byClass ->
      require(byClass.size == entries.size) {
        val duplicates =
          entries
            .groupBy { it.kclass }
            .filterValues { it.size > 1 }
            .keys
            .joinToString { it.simpleName.orEmpty() }
        "Duplicate ViewModel registrations for: $duplicates"
      }
    }

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(
    modelClass: KClass<T>,
    extras: CreationExtras,
  ): T {
    val entry =
      entriesByClass[modelClass] ?: error(
        "ViewModel ${modelClass.simpleName} is not registered. Annotate it with " +
          "@ContributesViewModel. Registered: " +
          entriesByClass.keys.joinToString { it.simpleName.orEmpty() },
      )
    return entry.create(assistedArgsFor(entry, extras)) as T
  }

  private fun assistedArgsFor(
    entry: ViewModelEntry,
    extras: CreationExtras,
  ): AssistedArgs {
    val args = extras[AssistedArgsKey] ?: EmptyAssistedArgs
    val handleArgName = entry.savedStateHandleArgName ?: return args
    val savedStateHandle: SavedStateHandle = extras.createSavedStateHandle()
    return args.withArg(handleArgName, savedStateHandle)
  }

  companion object {
    val AssistedArgsKey = object : CreationExtras.Key<AssistedArgs> {}
  }
}
