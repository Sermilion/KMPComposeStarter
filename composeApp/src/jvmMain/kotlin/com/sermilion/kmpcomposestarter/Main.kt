package com.sermilion.kmpcomposestarter

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sermilion.kmpcomposestarter.common.di.LocalScreenComponentFactory
import com.sermilion.kmpcomposestarter.common.di.LocalViewModelProvider
import com.sermilion.kmpcomposestarter.core.designsystem.theme.StarterTheme
import com.sermilion.kmpcomposestarter.core.ui.di.LocalUserComponentManager
import com.sermilion.kmpcomposestarter.core.ui.di.ProvideScreenComponentFactory
import com.sermilion.kmpcomposestarter.ui.rememberStarterAppState

fun main() = application {
  val component = remember { createJvmComponent() }

  Window(
    onCloseRequest = ::exitApplication,
    title = "KMP Compose Starter",
  ) {
    CompositionLocalProvider(
      LocalViewModelProvider provides component,
      LocalUserComponentManager provides component.userComponentManager,
    ) {
      ProvideScreenComponentFactory {
        StarterTheme {
          val screenComponentFactory = LocalScreenComponentFactory.current
          val appState = rememberStarterAppState(
            userComponentManager = component.userComponentManager,
            screenComponentFactory = screenComponentFactory,
          )
          StarterApp(
            appState = appState,
            isLoggedIn = appState.isAuthenticated,
            screenComponentFactory = screenComponentFactory,
          )
        }
      }
    }
  }
}
