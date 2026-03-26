package news.readian.notoesapp

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import news.readian.notoesapp.common.di.LocalScreenComponentFactory
import news.readian.notoesapp.common.di.LocalViewModelProvider
import news.readian.notoesapp.core.designsystem.theme.StarterTheme
import news.readian.notoesapp.core.ui.di.LocalUserComponentManager
import news.readian.notoesapp.core.ui.di.ProvideScreenComponentFactory
import news.readian.notoesapp.ui.rememberStarterAppState

@Suppress("FunctionName")
fun MainViewController() = ComposeUIViewController {
  val component = remember { createIosComponent() }

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
