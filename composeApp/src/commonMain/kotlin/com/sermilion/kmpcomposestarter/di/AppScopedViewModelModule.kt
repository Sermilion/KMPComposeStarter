package com.sermilion.kmpcomposestarter.di

import androidx.lifecycle.ViewModel
import com.sermilion.kmpcomposestarter.common.di.AssistedArgs
import com.sermilion.kmpcomposestarter.common.di.ViewModelEntry
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.LoginViewModel
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.RegisterViewModel
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import kotlin.reflect.KClass

@ContributesTo(AppScope::class)
interface AppScopedViewModelModule {

  @Provides
  @IntoSet
  fun provideLoginViewModelEntry(factory: () -> LoginViewModel): ViewModelEntry =
    object : ViewModelEntry {
      override val kclass: KClass<out ViewModel> = LoginViewModel::class
      override fun create(args: AssistedArgs): ViewModel = factory()
    }

  @Provides
  @IntoSet
  fun provideRegisterViewModelEntry(factory: () -> RegisterViewModel): ViewModelEntry =
    object : ViewModelEntry {
      override val kclass: KClass<out ViewModel> = RegisterViewModel::class
      override fun create(args: AssistedArgs): ViewModel = factory()
    }
}
