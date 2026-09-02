package com.sermilion.kmpcomposestarter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.sermilion.kmpcomposestarter.common.di.ScreenComponentProvider
import com.sermilion.kmpcomposestarter.navigation.StarterNavDisplay
import com.sermilion.kmpcomposestarter.navigation.StarterNavigationState
import com.sermilion.kmpcomposestarter.navigation.TopLevelTab
import com.sermilion.kmpcomposestarter.navigation.isTabRoot
import com.sermilion.kmpcomposestarter.ui.StarterAppState
import com.sermilion.kmpcomposestarter.ui.StarterBottomBar

@Composable
internal fun StarterApp(
  appState: StarterAppState,
  screenComponentFactory: (() -> ScreenComponentProvider)?,
  modifier: Modifier = Modifier,
) {
  val navigationState = appState.navigationState.value

  val canRenderAuthenticatedScreens =
    !navigationState.isAuthenticated || screenComponentFactory != null

  val isHandingOffSession = !canRenderAuthenticatedScreens
  NavigationBackHandler(
    state = rememberNavigationEventState(currentInfo = NavigationEventInfo.None),
    isBackEnabled = isHandingOffSession || navigationState.backAtTabRootShouldReturnHome,
    onBackCompleted = {
      if (!isHandingOffSession) appState.navigateToTab(TopLevelTab.HOME)
    },
  )

  Scaffold(
    modifier = modifier.fillMaxSize(),
    bottomBar = {
      if (canRenderAuthenticatedScreens && shouldShowBottomNavigation(navigationState)) {
        StarterBottomBar(
          currentTab = appState.currentTab,
          onTabSelect = appState::navigateToTab,
        )
      }
    },
  ) { innerPadding ->
    Box(
      modifier =
        Modifier
          .fillMaxSize()
          .padding(innerPadding),
      contentAlignment = Alignment.Center,
    ) {
      if (canRenderAuthenticatedScreens) {
        StarterNavDisplay(
          navigationState = navigationState,
          navigator = appState.navigator,
        )
      } else {
        CircularProgressIndicator()
      }
    }
  }
}

private fun shouldShowBottomNavigation(navigationState: StarterNavigationState): Boolean =
  navigationState.isAuthenticated && navigationState.currentRoute?.isTabRoot == true
