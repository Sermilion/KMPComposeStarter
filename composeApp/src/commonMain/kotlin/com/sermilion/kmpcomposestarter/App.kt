package com.sermilion.kmpcomposestarter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sermilion.kmpcomposestarter.navigation.StarterNavDisplay
import com.sermilion.kmpcomposestarter.navigation.StarterNavigationState
import com.sermilion.kmpcomposestarter.navigation.isTabRoot
import com.sermilion.kmpcomposestarter.ui.StarterAppState
import com.sermilion.kmpcomposestarter.ui.StarterBottomBar

@Composable
fun StarterApp(
  appState: StarterAppState,
  isLoggedIn: Boolean,
  screenComponentFactory:
  (() -> com.sermilion.kmpcomposestarter.common.di.ScreenComponentProvider)?,
  modifier: Modifier = Modifier,
) {
  val canRenderAuthenticatedScreens = !isLoggedIn || screenComponentFactory != null
  if (!canRenderAuthenticatedScreens) {
    return
  }

  val navigationState = appState.navigationState.value

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .then(modifier),
    bottomBar = {
      if (shouldShowBottomNavigation(navigationState, isLoggedIn)) {
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
    ) {
      StarterNavDisplay(
        navigationState = navigationState,
        navigator = appState.navigator,
        isLoggedIn = isLoggedIn,
      )
    }
  }
}

private fun shouldShowBottomNavigation(
  navigationState: StarterNavigationState,
  isLoggedIn: Boolean,
): Boolean = navigationState.isAuthenticated &&
  isLoggedIn &&
  navigationState.currentRoute?.isTabRoot == true
