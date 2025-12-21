package com.sermilion.kmpcomposestarter.common.di

import androidx.compose.runtime.staticCompositionLocalOf

val LocalViewModelFactory = staticCompositionLocalOf<ViewModelFactory> {
  error("ViewModelFactory not provided")
}

val LocalViewModelProvider = staticCompositionLocalOf<ViewModelProvider> {
  error("ViewModelProvider not provided")
}

val LocalScreenComponentFactory = staticCompositionLocalOf<() -> ScreenComponentProvider> {
  error("ScreenComponentFactory not provided")
}

interface ScreenComponentProvider {
  val diViewModelFactory: androidx.lifecycle.ViewModelProvider.Factory
}
