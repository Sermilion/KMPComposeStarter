package com.sermilion.kmpcomposestarter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sermilion.kmpcomposestarter.common.di.LocalPreAuthViewModelFactory
import com.sermilion.kmpcomposestarter.common.di.LocalScreenComponentFactory
import com.sermilion.kmpcomposestarter.core.designsystem.theme.StarterTheme
import com.sermilion.kmpcomposestarter.core.domain.session.SessionState
import com.sermilion.kmpcomposestarter.core.ui.di.LocalUserComponentManager
import com.sermilion.kmpcomposestarter.core.ui.di.ProvideScreenComponentFactory
import com.sermilion.kmpcomposestarter.di.AppComponent
import com.sermilion.kmpcomposestarter.ui.rememberStarterAppState

/** The single host wiring block. Android, iOS and desktop all render through this. */
@Composable
fun StarterRoot(component: AppComponent) {
  val sessionState by component.sessionRestorer.state.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) {
    component.sessionRestorer.restore()
  }

  CompositionLocalProvider(
    LocalPreAuthViewModelFactory provides component.viewModelFactory,
    LocalUserComponentManager provides component.userComponentManager,
  ) {
    ProvideScreenComponentFactory {
      StarterTheme {
        if (sessionState !is SessionState.Loading) {
          val screenComponentFactory = LocalScreenComponentFactory.current
          val appState =
            rememberStarterAppState(
              userComponentManager = component.userComponentManager,
              screenComponentFactory = screenComponentFactory,
            )
          StarterApp(
            appState = appState,
            screenComponentFactory = screenComponentFactory,
          )
        }
      }
    }
  }
}
