package news.readian.notoesapp.feature.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import news.readian.notoesapp.core.data.config.MockConfig
import news.readian.notoesapp.core.data.repository.AuthRepository
import news.readian.notoesapp.core.data.repository.LoginResult

@Inject
class WelcomeViewModel(private val authRepository: AuthRepository) : ViewModel() {
  private val mutableUiState = MutableStateFlow(WelcomeContract.UiState())
  val uiState: StateFlow<WelcomeContract.UiState> = mutableUiState.asStateFlow()

  fun onSkipClick() {
    if (uiState.value.isLoading) {
      return
    }

    viewModelScope.launch {
      mutableUiState.update { it.copy(isLoading = true, hasError = false) }
      when (
        authRepository.login(
          email = MockConfig.DEMO_EMAIL,
          password = MockConfig.DEMO_PASSWORD,
        )
      ) {
        is LoginResult.Success -> mutableUiState.update {
          it.copy(isLoading = false, hasError = false)
        }
        is LoginResult.Error -> mutableUiState.update {
          it.copy(isLoading = false, hasError = true)
        }
      }
    }
  }
}
