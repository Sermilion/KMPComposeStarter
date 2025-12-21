package com.sermilion.kmpcomposestarter.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sermilion.kmpcomposestarter.core.data.config.MockConfig
import com.sermilion.kmpcomposestarter.core.data.repository.AuthRepository
import com.sermilion.kmpcomposestarter.core.data.repository.LoginResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

  private val _uiState = MutableStateFlow(LoginContract.UiState())
  val uiState: StateFlow<LoginContract.UiState> = _uiState.asStateFlow()

  private val _events = MutableSharedFlow<LoginContract.Event>()
  val events: SharedFlow<LoginContract.Event> = _events.asSharedFlow()

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
        is LoginResult.Success -> {
          _uiState.update { it.copy(isLoading = false) }
          _events.emit(LoginContract.Event.LoginSuccess)
        }
        is LoginResult.Error -> {
          _uiState.update {
            it.copy(
              isLoading = false,
              error = LoginContract.Error.Unknown(result.message),
            )
          }
        }
      }
    }
  }

  fun loginDemo() {
    _uiState.update { it.copy(email = MockConfig.DEMO_EMAIL, password = MockConfig.DEMO_PASSWORD) }
    login()
  }

  fun navigateToRegister() {
    viewModelScope.launch {
      _events.emit(LoginContract.Event.NavigateToRegister)
    }
  }
}
