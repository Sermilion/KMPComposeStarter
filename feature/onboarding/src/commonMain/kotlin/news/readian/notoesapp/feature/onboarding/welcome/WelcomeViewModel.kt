
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
import news.readian.notoesapp.common.coroutines.DispatcherProvider
import news.readian.notoesapp.core.data.repository.AuthRepository
import news.readian.notoesapp.core.data.repository.LoginResult
import news.readian.notoesapp.feature.onboarding.common.ui.model.AnonRegProblem
import kotlin.time.Duration.Companion.seconds

@Inject
class WelcomeViewModel(
  private val authRepository: AuthRepository,
  private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

  private val loadingState = MutableStateFlow(false)
  private val errorState = MutableStateFlow(listOf<AnonRegProblem>())

  val uiState = combine(loadingState, errorState) { loading, errors ->
    WelcomeContract.UiState(loading = loading, errors = errors)
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
    initialValue = WelcomeContract.UiState(),
  )

  fun onSkipClick() {
    viewModelScope.launch(dispatcherProvider.io) {
      loadingState.update { true }
      errorState.update { listOf() }
      when (authRepository.loginGuest()) {
        is LoginResult.Success -> {
          errorState.update { listOf() }
        }
        is LoginResult.Error -> {
          errorState.update { listOf(AnonRegProblem.GenericError) }
        }
      }
      loadingState.update { false }
    }
  }
}
