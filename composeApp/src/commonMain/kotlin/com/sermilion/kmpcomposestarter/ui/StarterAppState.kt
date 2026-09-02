package com.sermilion.kmpcomposestarter.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sermilion.kmpcomposestarter.common.di.ScreenComponentProvider
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.navigation.StarterNavigationState
import com.sermilion.kmpcomposestarter.navigation.StarterNavigationStateSaver
import com.sermilion.kmpcomposestarter.navigation.StarterNavigator
import com.sermilion.kmpcomposestarter.navigation.TopLevelTab

@Composable
internal fun rememberStarterAppState(
  userComponentManager: UserComponentManager,
  screenComponentFactory: (() -> ScreenComponentProvider)?,
): StarterAppState {
  val userComponent by userComponentManager.userComponentFlow.collectAsStateWithLifecycle()
  val isLoggedIn = userComponent != null
  val canShowAuthenticated = isLoggedIn && screenComponentFactory != null

  val navigationState =
    rememberSaveable(stateSaver = StarterNavigationStateSaver) {
      mutableStateOf(StarterNavigationState(isAuthenticated = canShowAuthenticated))
    }
  val navigator = remember { StarterNavigator(navigationState) }

  val currentIsAuthenticated = navigationState.value.isAuthenticated
  LaunchedEffect(canShowAuthenticated, currentIsAuthenticated) {
    if (canShowAuthenticated && !currentIsAuthenticated) {
      navigator.onLoginStateChanged(true)
    } else if (!canShowAuthenticated && currentIsAuthenticated) {
      navigator.onLoginStateChanged(false)
    }
  }

  return remember(navigationState, navigator) {
    StarterAppState(
      mutableNavigationState = navigationState,
      navigator = navigator,
    )
  }
}

@Stable
internal class StarterAppState(
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
