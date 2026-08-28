package com.sermilion.kmpcomposestarter.common.di

import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface AppViewModelFactoryModule {

  @Provides
  @SingleIn(AppScope::class)
  fun provideAppViewModelFactory(entries: Set<ViewModelEntry>): StarterViewModelFactory =
    StarterViewModelFactory(entries)
}

@ContributesTo(ScreenScope::class)
interface ScreenViewModelFactoryModule {

  @Provides
  @SingleIn(ScreenScope::class)
  fun provideScreenViewModelFactory(entries: Set<ViewModelEntry>): StarterViewModelFactory =
    StarterViewModelFactory(entries)
}
