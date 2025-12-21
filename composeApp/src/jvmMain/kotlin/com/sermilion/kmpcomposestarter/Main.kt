package com.sermilion.kmpcomposestarter

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sermilion.kmpcomposestarter.common.di.LocalViewModelFactory
import com.sermilion.kmpcomposestarter.common.di.LocalViewModelProvider
import com.sermilion.kmpcomposestarter.core.designsystem.theme.StarterTheme

fun main() = application {
  val component = remember { createJvmComponent() }

  Window(
    onCloseRequest = ::exitApplication,
    title = "KMP Compose Starter",
  ) {
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
}
