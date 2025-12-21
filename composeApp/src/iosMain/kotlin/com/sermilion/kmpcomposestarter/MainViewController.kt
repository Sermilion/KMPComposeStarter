package com.sermilion.kmpcomposestarter

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.sermilion.kmpcomposestarter.common.di.LocalViewModelFactory
import com.sermilion.kmpcomposestarter.common.di.LocalViewModelProvider
import com.sermilion.kmpcomposestarter.core.designsystem.theme.StarterTheme

fun MainViewController() = ComposeUIViewController {
  val component = remember { createIosComponent() }

  CompositionLocalProvider(
    LocalViewModelFactory provides component.viewModelFactory,
    LocalViewModelProvider provides component,
  ) {
    StarterTheme {
      App(
        userComponentManager = component.userComponentManager,
        authRepository = component.authRepository,
      )
    }
  }
}
