package com.sermilion.kmpcomposestarter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.sermilion.kmpcomposestarter.common.di.LocalScreenComponentFactory
import com.sermilion.kmpcomposestarter.common.di.LocalViewModelFactory
import com.sermilion.kmpcomposestarter.core.designsystem.theme.StarterTheme
import com.sermilion.kmpcomposestarter.core.ui.di.LocalUserComponentManager
import com.sermilion.kmpcomposestarter.core.ui.di.ProvideScreenComponentFactory
import com.sermilion.kmpcomposestarter.di.AppComponent
import com.sermilion.kmpcomposestarter.ui.rememberStarterAppState

/** The single host wiring block. Android, iOS and desktop all render through this. */
@Composable
fun StarterRoot(component: AppComponent) {
  CompositionLocalProvider(
    LocalViewModelFactory provides component.viewModelFactory,
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
