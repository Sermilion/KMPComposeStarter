package news.readian.notoesapp.core.ui.di

import androidx.compose.runtime.staticCompositionLocalOf
import news.readian.notoesapp.core.domain.di.UserComponentManager

val LocalUserComponentManager = staticCompositionLocalOf<UserComponentManager> {
  error("UserComponentManager not provided")
}
