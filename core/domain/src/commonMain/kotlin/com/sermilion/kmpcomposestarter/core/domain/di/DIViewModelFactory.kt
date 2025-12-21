package com.sermilion.kmpcomposestarter.core.domain.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.common.di.AssistedArgs
import com.sermilion.kmpcomposestarter.common.di.EmptyAssistedArgs
import com.sermilion.kmpcomposestarter.common.di.ScreenScope
import com.sermilion.kmpcomposestarter.common.di.ViewModelEntry
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.reflect.KClass

@Inject
@SingleIn(ScreenScope::class)
class DIViewModelFactory(private val entries: Set<ViewModelEntry>) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
    val args = extras[AssistedArgsKey] ?: EmptyAssistedArgs

    val savedStateHandle = try {
      extras.createSavedStateHandle()
    } catch (e: Exception) {
      Logger.w(TAG) { "SavedStateHandle not available: ${e.message}" }
      null
    }

    val argsWithSavedState = if (savedStateHandle != null) {
      CombinedAssistedArgs(args, savedStateHandle)
    } else {
      args
    }

    val entry = entries.firstOrNull { it.kclass == modelClass }
      ?: run {
        val availableViewModels = entries.joinToString { it.kclass.simpleName.orEmpty() }
        Logger.e(TAG) {
          "ViewModel not found: ${modelClass.simpleName}. Available: $availableViewModels"
        }
        throw IllegalArgumentException(
          "ViewModel ${modelClass.simpleName} not found. " +
            "Did you forget to add @Inject annotation? " +
            "Available: $availableViewModels",
        )
      }

    return try {
      entry.create(argsWithSavedState) as T
    } catch (e: Exception) {
      Logger.e(TAG, e) { "Failed to create ViewModel: ${modelClass.simpleName}" }
      throw IllegalStateException(
        "Failed to create ViewModel ${modelClass.simpleName}: ${e.message}",
        e,
      )
    }
  }

  companion object {
    private const val TAG = "DIViewModelFactory"
    val AssistedArgsKey = object : CreationExtras.Key<AssistedArgs> {}
  }
}

private class CombinedAssistedArgs(
  private val original: AssistedArgs,
  private val savedStateHandle: SavedStateHandle,
) : AssistedArgs {
  @Suppress("UNCHECKED_CAST")
  override fun <T> get(name: String): T? = if (name == "savedStateHandle") {
    savedStateHandle as T
  } else {
    original[name]
  }
}
