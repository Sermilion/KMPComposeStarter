
package news.readian.notoesapp.feature.onboarding.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import news.readian.notoesapp.core.data.repository.AuthRepository
import news.readian.notoesapp.core.data.repository.LoginResult
import news.readian.notoesapp.feature.onboarding.common.ui.model.AnonRegProblem

@Inject
class WelcomeViewModel(private val authRepository: AuthRepository) : ViewModel() {

  private val loadingState = MutableStateFlow(false)
  private val errorState = MutableStateFlow(listOf<AnonRegProblem>())

  val uiState = combine(loadingState, errorState) { loading, errors ->
    WelcomeContract.UiState(loading = loading, errors = errors)
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
    initialValue = WelcomeContract.UiState(),
  )

  fun onSkipClick() {
    if (loadingState.value) return

    viewModelScope.launch {
      loadingState.update { true }
      errorState.update { emptyList() }
      try {
        when (authRepository.loginGuest()) {
          is LoginResult.Success -> errorState.update { emptyList() }
          is LoginResult.Error -> errorState.update { listOf(AnonRegProblem.GenericError) }
        }
      } finally {
        loadingState.update { false }
      }
    }
  }

  private companion object {
    const val STOP_TIMEOUT_MILLIS = 5_000L
  }
}
