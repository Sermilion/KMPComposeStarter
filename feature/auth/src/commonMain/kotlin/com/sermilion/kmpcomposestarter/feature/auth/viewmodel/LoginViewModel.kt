package com.sermilion.kmpcomposestarter.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sermilion.kmpcomposestarter.common.coroutines.Effect
import com.sermilion.kmpcomposestarter.common.di.ContributesViewModel
import com.sermilion.kmpcomposestarter.core.domain.model.AuthError
import com.sermilion.kmpcomposestarter.core.domain.model.DemoCredentials
import com.sermilion.kmpcomposestarter.core.domain.model.LoginResult
import com.sermilion.kmpcomposestarter.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope

@Inject
@ContributesViewModel(AppScope::class)
class LoginViewModel(
  private val authRepository: AuthRepository,
  private val demoCredentials: DemoCredentials,
) : ViewModel() {

  private val _uiState = MutableStateFlow(LoginContract.UiState())
  val uiState: StateFlow<LoginContract.UiState> = _uiState.asStateFlow()

  private val _effects = Effect<LoginContract.Event>()
  val effects: Flow<LoginContract.Event> = _effects.flow

  fun onEmailChange(email: String) {
    _uiState.update { it.copy(email = email, error = null) }
  }

  fun onPasswordChange(password: String) {
    _uiState.update { it.copy(password = password, error = null) }
  }

  fun login() {
    val state = _uiState.value
    if (state.isLoading) return

    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, error = null) }

      when (val result = authRepository.login(state.email, state.password)) {
        // No success event: the session flow the repository writes is the single signal that
        // moves the app to the signed-in shell.
        is LoginResult.Success -> _uiState.update { it.copy(isLoading = false) }
        is LoginResult.Failure -> {
          _uiState.update { it.copy(isLoading = false, error = result.error.toUiError()) }
        }
      }
    }
  }

  fun loginDemo() {
    _uiState.update {
      it.copy(email = demoCredentials.loginEmail, password = demoCredentials.password)
    }
    login()
  }

  fun navigateToRegister() {
    viewModelScope.launch {
      _effects.emit(LoginContract.Event.NavigateToRegister)
    }
  }

  private fun AuthError.toUiError(): LoginContract.Error = when (this) {
    AuthError.InvalidCredentials -> LoginContract.Error.InvalidCredentials
    AuthError.Network -> LoginContract.Error.Network
    AuthError.RefreshFailed, is AuthError.Unexpected -> LoginContract.Error.Unknown
  }
}
