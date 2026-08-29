package com.sermilion.kmpcomposestarter.common.di

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModelProvider

/** Factory for ViewModels that must resolve before a user session exists (login, register). */
val LocalPreAuthViewModelFactory =
  staticCompositionLocalOf<ViewModelProvider.Factory> {
    error("ViewModel factory not provided")
  }

/** `null` until a user session exists; creates one screen component per nav entry. */
val LocalScreenComponentFactory =
  staticCompositionLocalOf<(() -> ScreenComponentProvider)?> {
    null
  }

interface ScreenComponentProvider {
  val viewModelFactory: ViewModelProvider.Factory
}
