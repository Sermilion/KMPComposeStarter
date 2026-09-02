package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailRoute
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailScreen
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailViewModel
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
import com.sermilion.kmpcomposestarter.feature.settings.viewmodel.SettingsViewModel

/**
 * The one place a route is bound to a screen.
 *
 * Every route is registered unconditionally: which entries are reachable is decided by the back
 * stack the display is handed, not by a gate here, so a signed-out user can no more reach
 * [HomeRoute] than a registration that forgot its entry can silently render nothing.
 *
 * Any route added here must also be registered in [starterSerializersModule], or the first
 * process-death restore fails on it. `StarterRouteSerializationTest` is the check for that.
 */
@Suppress("LongMethod")
internal fun createStarterEntryProvider(navigator: StarterNavigator) =
  entryProvider<Route> {
    entry<LoginRoute> {
      val viewModel = injectViewModel<LoginViewModel>(scope = ViewModelScope.PreAuth)
      val uiState by viewModel.uiState.collectAsStateWithLifecycle()
      LaunchedEffect(Unit) {
        viewModel.effects.collect { event ->
          when (event) {
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
      val viewModel = injectViewModel<RegisterViewModel>(scope = ViewModelScope.PreAuth)
      val uiState by viewModel.uiState.collectAsStateWithLifecycle()
      LaunchedEffect(Unit) {
        viewModel.effects.collect { event ->
          when (event) {
            RegisterContract.Event.NavigateBack -> navigator.goBack()
          }
        }
      }
      RegisterScreen(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRegisterClick = viewModel::register,
        onRegisterDemo = viewModel::registerDemo,
        onNavigateBack = viewModel::navigateBack,
      )
    }

    entry<HomeRoute> {
      val viewModel = injectViewModel<HomeViewModel>()
      val uiState by viewModel.uiState.collectAsStateWithLifecycle()
      LaunchedEffect(Unit) {
        viewModel.effects.collect { event ->
          when (event) {
            HomeContract.Event.NavigateToProfile ->
              navigator.navigateToTopLevel(
                TopLevelTab.PROFILE,
              )
            is HomeContract.Event.NavigateToDetail -> navigator.navigate(DetailRoute(event.id))
          }
        }
      }
      HomeScreen(
        uiState = uiState,
        onNavigateToProfile = viewModel::navigateToProfile,
        onOpenDetail = viewModel::openDetail,
      )
    }

    entry<DetailRoute> { route ->
      // The assisted key must match DetailViewModel's constructor parameter name exactly: that
      // name is the contract the generated entry reads the argument back out under.
      val viewModel = injectViewModel<DetailViewModel>(assisted = mapOf("id" to route.id))
      val uiState by viewModel.uiState.collectAsStateWithLifecycle()
      DetailScreen(
        uiState = uiState,
        onNoteChange = viewModel::onNoteChange,
        onNavigateBack = { navigator.goBack() },
      )
    }

    entry<ProfileRoute> {
      val viewModel = injectViewModel<ProfileViewModel>()
      val uiState by viewModel.uiState.collectAsStateWithLifecycle()
      LaunchedEffect(Unit) {
        viewModel.effects.collect { event ->
          when (event) {
            ProfileContract.Event.NavigateBack -> navigator.goBack()
          }
        }
      }
      ProfileScreen(
        uiState = uiState,
        onNavigateBack = viewModel::navigateBack,
        onLogout = viewModel::logout,
        onDeleteMyData = viewModel::deleteMyData,
      )
    }

    entry<SettingsRoute> {
      val viewModel = injectViewModel<SettingsViewModel>()
      val uiState by viewModel.uiState.collectAsStateWithLifecycle()
      SettingsScreen(
        uiState = uiState,
      )
    }
  }
