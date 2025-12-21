package com.sermilion.kmpcomposestarter.core.domain.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.sermilion.kmpcomposestarter.common.di.SingleIn
import com.sermilion.kmpcomposestarter.common.di.UserFeatureScope
import me.tatarka.inject.annotations.Inject
import kotlin.reflect.KClass

interface ViewModelEntry {
  val viewModelClass: KClass<out ViewModel>
  fun create(): ViewModel
}

@Inject
@SingleIn(UserFeatureScope::class)
class DIViewModelFactory(
  private val entries: Set<ViewModelEntry>
) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
    val entry = entries.find { it.viewModelClass == modelClass }
      ?: throw IllegalArgumentException("ViewModel not found: ${modelClass.simpleName}")
    return entry.create() as T
  }
}
