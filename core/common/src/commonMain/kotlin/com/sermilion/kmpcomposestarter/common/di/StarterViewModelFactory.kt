package com.sermilion.kmpcomposestarter.common.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.reflect.KClass

/**
 * The ViewModel factory shared by every scope. One instance exists per DI scope; the scope decides
 * which [ViewModelEntry] multibindings are visible.
 *
 * Abstract on purpose, with one concrete subclass per scope. kotlin-inject matches providers by
 * type, and the app component exposes its factory as an accessor that every descendant component
 * can see. A single concrete type therefore made the screen component resolve the app component's
 * accessor instead of its own provider, and every screen-scoped ViewModel was missing from the only
 * factory the screens ever used. A distinct type per scope makes that a compile error rather than a
 * crash on first navigation.
 */
abstract class StarterViewModelFactory(
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

  /** The ViewModels this factory can create. Lets a test pin what a scope actually registered. */
  val registeredViewModels: Set<KClass<out ViewModel>> get() = entriesByClass.keys

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

/**
 * The app-scope factory: the pre-auth screens, which resolve before a session exists.
 */
class PreAuthViewModelFactory(
  entries: Set<ViewModelEntry>,
) : StarterViewModelFactory(entries)

/**
 * The screen-scope factory: one per nav entry, serving that entry's ViewModels.
 */
class ScreenViewModelFactory(
  entries: Set<ViewModelEntry>,
) : StarterViewModelFactory(entries)
