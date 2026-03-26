package news.readian.notoesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import news.readian.notoesapp.common.di.LocalScreenComponentFactory
import news.readian.notoesapp.common.di.LocalViewModelProvider
import news.readian.notoesapp.core.designsystem.theme.StarterTheme
import news.readian.notoesapp.core.ui.di.LocalUserComponentManager
import news.readian.notoesapp.core.ui.di.ProvideScreenComponentFactory
import news.readian.notoesapp.ui.rememberStarterAppState

class MainActivity : ComponentActivity() {

  private val component: AndroidApplicationComponent by lazy {
    (application as NotesApplication).component
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
