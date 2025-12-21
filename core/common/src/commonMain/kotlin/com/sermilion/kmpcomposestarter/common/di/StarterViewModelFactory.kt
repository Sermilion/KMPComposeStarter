package com.sermilion.kmpcomposestarter.common.di

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.reflect.KClass

@Inject
@SingleIn(AppScope::class)
class StarterViewModelFactory(private val entries: Set<ViewModelEntry>) :
  ViewModelFactory,
  ViewModelProvider {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(viewModelClass: KClass<T>): T {
    val entry = entries.firstOrNull { it.kclass == viewModelClass }
      ?: run {
        val availableViewModels = entries.joinToString { it.kclass.simpleName.orEmpty() }
        Logger.e(TAG) {
          "ViewModel not found: ${viewModelClass.simpleName}. Available: $availableViewModels"
        }
        throw IllegalArgumentException(
          "ViewModel ${viewModelClass.simpleName} not found. " +
            "Did you forget to add @Inject annotation? " +
            "Available: $availableViewModels",
        )
      }
    return entry.create(EmptyAssistedArgs) as T
  }

  override fun <T : ViewModel> provide(viewModelClass: KClass<T>): T = create(viewModelClass)

  private companion object {
    const val TAG = "StarterViewModelFactory"
  }
}
