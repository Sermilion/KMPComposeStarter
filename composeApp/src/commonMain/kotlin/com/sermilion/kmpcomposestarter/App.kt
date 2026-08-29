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
fun StarterApp(
  appState: StarterAppState,
  screenComponentFactory: (() -> ScreenComponentProvider)?,
  modifier: Modifier = Modifier,
) {
  val navigationState = appState.navigationState.value

  // Authenticated screens resolve their ViewModels through the screen component. Between a
  // session ending and the navigation state following it the factory is briefly null, and asking
  // for one then throws. This composable never returns early over that: it renders a placeholder,
  // so a logout can never blank the frame.
  val canRenderAuthenticatedScreens =
    !navigationState.isAuthenticated || screenComponentFactory != null

  // The host half of the back policy. NavDisplay disables its own handler once there is nothing
  // left to pop, so back at a non-HOME tab root would otherwise leave the app. Back at the HOME
  // root and on the single-entry auth stack stays disabled here too, and falls through to the
  // system.
  //
  // During the session hand-off no NavDisplay is composed at all, so the handler stays enabled
  // and swallows back: a stray press in that window must not close the app.
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
          onTabSelected = appState::navigateToTab,
        )
      }
    },
  ) { innerPadding ->
    Box(
      modifier = Modifier
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
        // Deliberately unbranded: this is the hand-off between two sessions, not a screen.
        CircularProgressIndicator()
      }
    }
  }
}

private fun shouldShowBottomNavigation(navigationState: StarterNavigationState): Boolean =
  navigationState.isAuthenticated && navigationState.currentRoute?.isTabRoot == true
