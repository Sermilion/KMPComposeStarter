package com.sermilion.kmpcomposestarter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.sermilion.kmpcomposestarter.common.di.LocalScreenComponentFactory
import com.sermilion.kmpcomposestarter.common.di.LocalViewModelProvider
import com.sermilion.kmpcomposestarter.core.designsystem.theme.StarterTheme
import com.sermilion.kmpcomposestarter.core.ui.di.LocalUserComponentManager
import com.sermilion.kmpcomposestarter.core.ui.di.ProvideScreenComponentFactory
import com.sermilion.kmpcomposestarter.ui.rememberStarterAppState

class MainActivity : ComponentActivity() {

  private val component: AndroidApplicationComponent by lazy {
    (application as StarterApplication).component
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
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
}
