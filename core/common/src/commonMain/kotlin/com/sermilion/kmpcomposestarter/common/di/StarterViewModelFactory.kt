package com.sermilion.kmpcomposestarter.common.di

import androidx.lifecycle.ViewModel
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import kotlin.reflect.KClass

@Inject
@SingleIn(AppScope::class)
class StarterViewModelFactory(
  private val creators: Map<KClass<out ViewModel>, () -> ViewModel>
) : ViewModelFactory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(viewModelClass: KClass<T>): T {
    val creator = creators[viewModelClass]
      ?: creators.entries.firstOrNull { viewModelClass == it.key }?.value
      ?: throw IllegalArgumentException("ViewModel not found: ${viewModelClass.simpleName}")
    return creator() as T
  }
}
