package news.readian.notoesapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import news.readian.notoesapp.common.navigation.Route
import news.readian.notoesapp.core.domain.di.UserComponentManager
import news.readian.notoesapp.navigation.StarterNavigationState
import news.readian.notoesapp.navigation.StarterNavigator
import news.readian.notoesapp.navigation.TopLevelTab

@Composable
fun rememberStarterAppState(
  userComponentManager: UserComponentManager,
  screenComponentFactory: (() -> news.readian.notoesapp.common.di.ScreenComponentProvider)?,
): StarterAppState {
  val userComponent by userComponentManager.userComponentFlow.collectAsState()
  val isLoggedIn = userComponent != null
  val canShowAuthenticated = isLoggedIn && screenComponentFactory != null

  val navigationState = remember { mutableStateOf(StarterNavigationState()) }
  val navigator = remember { StarterNavigator(navigationState) }

  LaunchedEffect(canShowAuthenticated, navigationState.value.isAuthenticated) {
    syncAuthenticationState(
      navigator = navigator,
      canShowAuthenticated = canShowAuthenticated,
      currentIsAuthenticated = navigationState.value.isAuthenticated,
    )
  }

  return remember(navigationState, navigator) {
    StarterAppState(
      mutableNavigationState = navigationState,
      navigator = navigator,
    )
  }
}

internal fun syncAuthenticationState(
  navigator: StarterNavigator,
  canShowAuthenticated: Boolean,
  currentIsAuthenticated: Boolean,
) {
  if (canShowAuthenticated && !currentIsAuthenticated) {
    navigator.onLoginStateChanged(true)
  } else if (!canShowAuthenticated && currentIsAuthenticated) {
    navigator.onLoginStateChanged(false)
  }
}

@Stable
class StarterAppState(
  private val mutableNavigationState: MutableState<StarterNavigationState>,
  val navigator: StarterNavigator,
) {
  val navigationState: State<StarterNavigationState>
    get() = mutableNavigationState

  val currentRoute: Route?
    get() = mutableNavigationState.value.currentRoute

  val currentTab: TopLevelTab
    get() = mutableNavigationState.value.currentTab

  val isAuthenticated: Boolean
    get() = mutableNavigationState.value.isAuthenticated

  fun navigateToTab(tab: TopLevelTab) {
    navigator.navigateToTopLevel(tab)
  }
}
