package news.readian.notoesapp.core.ui.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import news.readian.notoesapp.common.di.LocalScreenComponentFactory
import news.readian.notoesapp.common.di.ScreenComponentProvider

@Composable
fun ProvideScreenComponentFactory(content: @Composable () -> Unit) {
  val userComponentManager = LocalUserComponentManager.current
  val userComponent by userComponentManager.userComponentFlow.collectAsState()

  val factory = remember(userComponent) {
    userComponent?.let { component ->
      {
        object : ScreenComponentProvider {
          override val diViewModelFactory =
            component.screenComponentFactory.create().diViewModelFactory
        }
      }
    }
  }

  CompositionLocalProvider(LocalScreenComponentFactory provides factory) {
    content()
  }
}
