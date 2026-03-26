package news.readian.notoesapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import news.readian.notoesapp.navigation.StarterNavDisplay
import news.readian.notoesapp.navigation.StarterNavigationState
import news.readian.notoesapp.navigation.isTabRoot
import news.readian.notoesapp.ui.StarterAppState
import news.readian.notoesapp.ui.StarterBottomBar

@Composable
fun StarterApp(
  appState: StarterAppState,
  isLoggedIn: Boolean,
  screenComponentFactory: (() -> news.readian.notoesapp.common.di.ScreenComponentProvider)?,
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
