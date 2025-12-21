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
class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

  private val _uiState = MutableStateFlow(RegisterContract.UiState())
  val uiState: StateFlow<RegisterContract.UiState> = _uiState.asStateFlow()

  private val _events = MutableSharedFlow<RegisterContract.Event>()
  val events: SharedFlow<RegisterContract.Event> = _events.asSharedFlow()

  fun onNameChange(name: String) {
    _uiState.update { it.copy(name = name, error = null) }
  }

  fun onEmailChange(email: String) {
    _uiState.update { it.copy(email = email, error = null) }
  }

  fun onPasswordChange(password: String) {
    _uiState.update { it.copy(password = password, error = null) }
  }

  fun register() {
    val state = _uiState.value
    if (state.isLoading) return

    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, error = null) }

      when (val result = authRepository.register(state.email, state.password, state.name)) {
        is LoginResult.Success -> {
          _uiState.update { it.copy(isLoading = false) }
          _events.emit(RegisterContract.Event.RegisterSuccess)
        }
        is LoginResult.Error -> {
          _uiState.update {
            it.copy(
              isLoading = false,
              error = RegisterContract.Error.Unknown(result.message),
            )
          }
        }
      }
    }
  }

  fun registerDemo() {
    _uiState.update {
      it.copy(name = "New User", email = "new@test.com", password = MockConfig.DEMO_PASSWORD)
    }
    register()
  }

  fun navigateBack() {
    viewModelScope.launch {
      _events.emit(RegisterContract.Event.NavigateBack)
    }
  }
}
