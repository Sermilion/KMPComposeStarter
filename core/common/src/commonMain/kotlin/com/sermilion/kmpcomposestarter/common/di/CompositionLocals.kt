package com.sermilion.kmpcomposestarter.common.di

import androidx.compose.runtime.staticCompositionLocalOf

val LocalViewModelFactory = staticCompositionLocalOf<ViewModelFactory> {
  error("ViewModelFactory not provided")
}

val LocalViewModelProvider = staticCompositionLocalOf<ViewModelProvider> {
  error("ViewModelProvider not provided")
}

val LocalScreenComponentFactory = staticCompositionLocalOf<(() -> ScreenComponentProvider)?> {
  null
}

interface ScreenComponentProvider {
  val diViewModelFactory: androidx.lifecycle.ViewModelProvider.Factory
}
