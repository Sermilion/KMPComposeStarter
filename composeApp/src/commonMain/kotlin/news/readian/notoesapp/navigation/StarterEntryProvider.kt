
package news.readian.notoesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation3.runtime.entryProvider
import news.readian.notoesapp.common.navigation.Route
import news.readian.notoesapp.core.ui.navigation.ViewModelScope
import news.readian.notoesapp.core.ui.navigation.injectViewModel
import news.readian.notoesapp.feature.auth.navigation.LoginRoute
import news.readian.notoesapp.feature.auth.navigation.RegisterRoute
import news.readian.notoesapp.feature.auth.ui.LoginScreen
import news.readian.notoesapp.feature.auth.ui.RegisterScreen
import news.readian.notoesapp.feature.auth.viewmodel.LoginViewModel
import news.readian.notoesapp.feature.auth.viewmodel.RegisterViewModel
import news.readian.notoesapp.feature.home.navigation.HomeRoute
import news.readian.notoesapp.feature.home.ui.HomeScreen
import news.readian.notoesapp.feature.home.viewmodel.HomeContract
import news.readian.notoesapp.feature.home.viewmodel.HomeViewModel
import news.readian.notoesapp.feature.onboarding.navigation.TutorialRoute
import news.readian.notoesapp.feature.onboarding.navigation.WelcomeRoute
import news.readian.notoesapp.feature.onboarding.tutorial.TutorialViewModel
import news.readian.notoesapp.feature.onboarding.tutorial.ui.TutorialScreen
import news.readian.notoesapp.feature.onboarding.ui.WelcomeScreen
import news.readian.notoesapp.feature.onboarding.welcome.WelcomeViewModel
import news.readian.notoesapp.feature.profile.navigation.ProfileRoute
import news.readian.notoesapp.feature.profile.ui.ProfileScreen
import news.readian.notoesapp.feature.profile.viewmodel.ProfileContract
import news.readian.notoesapp.feature.profile.viewmodel.ProfileViewModel
import news.readian.notoesapp.feature.settings.navigation.SettingsRoute
import news.readian.notoesapp.feature.settings.ui.SettingsScreen
import news.readian.notoesapp.feature.settings.viewmodel.SettingsContract
import news.readian.notoesapp.feature.settings.viewmodel.SettingsViewModel

@Suppress("LongMethod")
@Composable
fun createStarterEntryProvider(navigator: StarterNavigator, isLoggedIn: Boolean) =
  entryProvider<Route> {
    entry<TutorialRoute> {
      val viewModel = injectViewModel<TutorialViewModel>(scope = ViewModelScope.Onboarding)
      TutorialScreen(
        viewModel = viewModel,
        onLoginOrRegisterClick = { navigator.navigate(WelcomeRoute) },
      )
    }

    entry<WelcomeRoute> {
      val viewModel = injectViewModel<WelcomeViewModel>(scope = ViewModelScope.Onboarding)
      WelcomeScreen(
        onLoginClick = { navigator.navigate(LoginRoute) },
        onSignUpClick = { navigator.navigate(RegisterRoute) },
        viewModel = viewModel,
      )
    }

    entry<LoginRoute> {
      val viewModel = injectViewModel<LoginViewModel>(scope = ViewModelScope.Onboarding)
      LoginScreen(
        viewModel = viewModel,
        onForgotPasswordClick = { },
        onBackClick = { navigator.goBack() },
      )
    }

    entry<RegisterRoute> {
      val viewModel = injectViewModel<RegisterViewModel>(scope = ViewModelScope.Onboarding)
      RegisterScreen(
        viewModel = viewModel,
        onBackClick = { navigator.goBack() },
      )
    }

    if (isLoggedIn) {
      entry<HomeRoute> {
        val viewModel = injectViewModel<HomeViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        LaunchedEffect(Unit) {
          viewModel.events.collect { event ->
            when (event) {
              HomeContract.Event.NavigateToProfile -> navigator.navigateToTopLevel(
                TopLevelTab.PROFILE,
              )
            }
          }
        }
        HomeScreen(
          uiState = uiState,
          onNavigateToProfile = viewModel::navigateToProfile,
        )
      }

      entry<ProfileRoute> {
        val viewModel = injectViewModel<ProfileViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        LaunchedEffect(Unit) {
          viewModel.events.collect { event ->
            when (event) {
              ProfileContract.Event.NavigateBack -> navigator.goBack()
              ProfileContract.Event.LogoutSuccess -> { }
            }
          }
        }
        ProfileScreen(
          uiState = uiState,
          onNavigateBack = viewModel::navigateBack,
          onLogout = viewModel::logout,
        )
      }

      entry<SettingsRoute> {
        val viewModel = injectViewModel<SettingsViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        LaunchedEffect(Unit) {
          viewModel.events.collect { event ->
            when (event) {
              SettingsContract.Event.NavigateBack -> navigator.goBack()
            }
          }
        }
        SettingsScreen(uiState = uiState)
      }
    }
  }
