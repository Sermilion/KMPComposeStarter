
package news.readian.notoesapp.feature.auth.viewmodel

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
import kotlin.time.Duration.Companion.seconds

@Inject
class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

  private val loadingState = MutableStateFlow(false)
  private val errorState = MutableStateFlow(listOf<LoginContract.LoginProblem>())

  val uiState = combine(loadingState, errorState) { loading, errors ->
    LoginContract.UiState(loading = loading, errors = errors)
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
    initialValue = LoginContract.UiState(),
  )

  fun onLogin(identifier: String, password: String) {
    viewModelScope.launch {
      loadingState.update { true }
      errorState.update { listOf() }
      when (val result = authRepository.login(identifier, password)) {
        is LoginResult.Success -> errorState.update { listOf() }
        is LoginResult.Error -> errorState.update { listOf(result.toUiProblem()) }
      }
      loadingState.update { false }
    }
  }

  private fun LoginResult.Error.toUiProblem(): LoginContract.LoginProblem {
    val normalizedMessage = message.lowercase()
    val responseStatusCode = statusCode
    return when {
      responseStatusCode == UNAUTHORIZED_STATUS_CODE ||
        normalizedMessage.contains(
          "credential",
        ) -> {
        LoginContract.LoginProblem.InvalidCredentials
      }
      responseStatusCode == TOO_MANY_REQUESTS_STATUS_CODE -> LoginContract.LoginProblem.RateLimited
      responseStatusCode != null && responseStatusCode >= SERVER_ERROR_THRESHOLD -> {
        LoginContract.LoginProblem.ServerError
      }
      normalizedMessage.contains("timeout") || normalizedMessage.contains("timed out") -> {
        LoginContract.LoginProblem.NetworkTimeout
      }
      responseStatusCode == UNPROCESSABLE_ENTITY_STATUS_CODE -> {
        LoginContract.LoginProblem.Validation(
          listOf(LoginContract.Field.Unknown),
        )
      }
      else -> LoginContract.LoginProblem.GenericError
    }
  }

  private companion object {
    const val UNAUTHORIZED_STATUS_CODE = 401
    const val UNPROCESSABLE_ENTITY_STATUS_CODE = 422
    const val TOO_MANY_REQUESTS_STATUS_CODE = 429
    const val SERVER_ERROR_THRESHOLD = 500
  }
}
