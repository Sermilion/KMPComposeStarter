package com.sermilion.kmpcomposestarter.feature.auth.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.core.ui.navigation.ViewModelScope
import com.sermilion.kmpcomposestarter.core.ui.navigation.injectViewModel
import com.sermilion.kmpcomposestarter.feature.auth.ui.LoginScreen
import com.sermilion.kmpcomposestarter.feature.auth.ui.RegisterScreen
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.LoginContract
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.LoginViewModel
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.RegisterContract
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.RegisterViewModel

/**
 * Binds this feature's routes to its screens.
 *
 * The feature owns its own wiring so adding a screen here is one edit in one module. Navigation is
 * taken as callbacks rather than a navigator: the back stack is the app shell's to own, and a
 * feature that could push arbitrary routes onto it would be a feature that has to know about every
 * other feature.
 */
fun EntryProviderScope<Route>.authEntries(
  onNavigateToRegister: () -> Unit,
  onNavigateBack: () -> Unit,
) {
  entry<LoginRoute> {
    val viewModel = injectViewModel<LoginViewModel>(scope = ViewModelScope.PreAuth)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
      viewModel.effects.collect { event ->
        when (event) {
          LoginContract.Event.NavigateToRegister -> onNavigateToRegister()
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
          RegisterContract.Event.NavigateBack -> onNavigateBack()
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
}
