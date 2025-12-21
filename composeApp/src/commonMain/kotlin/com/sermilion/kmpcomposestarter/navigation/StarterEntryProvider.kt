package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation3.runtime.entryProvider
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.core.ui.navigation.ViewModelScope
import com.sermilion.kmpcomposestarter.core.ui.navigation.injectViewModel
import com.sermilion.kmpcomposestarter.feature.auth.navigation.LoginRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.RegisterRoute
import com.sermilion.kmpcomposestarter.feature.auth.ui.LoginScreen
import com.sermilion.kmpcomposestarter.feature.auth.ui.RegisterScreen
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.LoginContract
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.LoginViewModel
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.RegisterContract
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.RegisterViewModel
import com.sermilion.kmpcomposestarter.feature.home.navigation.HomeRoute
import com.sermilion.kmpcomposestarter.feature.home.ui.HomeScreen
import com.sermilion.kmpcomposestarter.feature.home.viewmodel.HomeContract
import com.sermilion.kmpcomposestarter.feature.home.viewmodel.HomeViewModel
import com.sermilion.kmpcomposestarter.feature.profile.navigation.ProfileRoute
import com.sermilion.kmpcomposestarter.feature.profile.ui.ProfileScreen
import com.sermilion.kmpcomposestarter.feature.profile.viewmodel.ProfileContract
import com.sermilion.kmpcomposestarter.feature.profile.viewmodel.ProfileViewModel
import com.sermilion.kmpcomposestarter.feature.settings.navigation.SettingsRoute
import com.sermilion.kmpcomposestarter.feature.settings.ui.SettingsScreen
import com.sermilion.kmpcomposestarter.feature.settings.viewmodel.SettingsContract
import com.sermilion.kmpcomposestarter.feature.settings.viewmodel.SettingsViewModel

@Suppress("LongMethod")
@Composable
fun createStarterEntryProvider(navigator: StarterNavigator, isLoggedIn: Boolean) =
  entryProvider<Route> {
    entry<LoginRoute> {
      val viewModel = injectViewModel<LoginViewModel>(scope = ViewModelScope.Onboarding)
      val uiState by viewModel.uiState.collectAsState()
      LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
          when (event) {
            LoginContract.Event.LoginSuccess -> { }
            LoginContract.Event.NavigateToRegister -> navigator.navigate(RegisterRoute)
          }
        }
      }
      LoginScreen(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::login,
        onDemoLoginClick = viewModel::loginDemo,
        onNavigateToRegister = viewModel::navigateToRegister,
      )
    }

    entry<RegisterRoute> {
      val viewModel = injectViewModel<RegisterViewModel>(scope = ViewModelScope.Onboarding)
      val uiState by viewModel.uiState.collectAsState()
      LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
          when (event) {
            RegisterContract.Event.RegisterSuccess -> { }
            RegisterContract.Event.NavigateBack -> navigator.goBack()
          }
        }
      }
      RegisterScreen(
        uiState = uiState,
        onRegisterDemo = viewModel::registerDemo,
        onNavigateBack = viewModel::navigateBack,
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
        SettingsScreen(
          uiState = uiState,
        )
      }
    }
  }
