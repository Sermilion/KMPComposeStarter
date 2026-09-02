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
class RegisterViewModel(
  private val authRepository: AuthRepository,
  private val demoCredentials: DemoCredentials,
) : ViewModel() {
  private val _uiState = MutableStateFlow(RegisterContract.UiState())
  val uiState: StateFlow<RegisterContract.UiState> = _uiState.asStateFlow()

  private val _effects = Effect<RegisterContract.Event>()
  val effects: Flow<RegisterContract.Event> = _effects.flow

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
        is LoginResult.Success -> _uiState.update { it.copy(isLoading = false) }
        is LoginResult.Failure -> {
          _uiState.update { it.copy(isLoading = false, error = result.error.toUiError()) }
        }
      }
    }
  }

  fun registerDemo() {
    _uiState.update {
      it.copy(
        name = demoCredentials.newUserName,
        email = demoCredentials.newUserEmail,
        password = demoCredentials.password,
      )
    }
    register()
  }

  fun navigateBack() {
    viewModelScope.launch {
      _effects.emit(RegisterContract.Event.NavigateBack)
    }
  }

  private fun AuthError.toUiError(): RegisterContract.Error =
    when (this) {
      AuthError.InvalidCredentials -> RegisterContract.Error.RegistrationFailed
      AuthError.Network -> RegisterContract.Error.Network
      AuthError.RefreshFailed, is AuthError.Unexpected -> RegisterContract.Error.Unknown
    }
}
