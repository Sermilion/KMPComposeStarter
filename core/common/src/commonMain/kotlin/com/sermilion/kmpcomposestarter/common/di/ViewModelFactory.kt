package com.sermilion.kmpcomposestarter.common.di

import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

interface ViewModelFactory {
  fun <T : ViewModel> create(viewModelClass: KClass<T>): T
}

interface ViewModelProvider {
  fun <T : ViewModel> provide(viewModelClass: KClass<T>): T
}
