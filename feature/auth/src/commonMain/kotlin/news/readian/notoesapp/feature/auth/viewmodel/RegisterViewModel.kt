
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

@Inject
class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

  private val loadingState = MutableStateFlow(false)
  private val errorState = MutableStateFlow(listOf<RegisterContract.RegistrationProblem>())
  private val mutableNavigationState = MutableStateFlow<RegisterContract.NavigationState>(
    RegisterContract.NavigationState.Registration,
  )

  val navigationState = mutableNavigationState.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
    initialValue = RegisterContract.NavigationState.Registration,
  )

  val uiState = combine(loadingState, errorState) { loading, errors ->
    RegisterContract.UiState.Content(loading = loading, errors = errors)
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
    initialValue = RegisterContract.UiState.Initial,
  )

  fun onSignUpClick(username: String, email: String, password: String, confirmPassword: String) {
    viewModelScope.launch {
      handleRegistration(
        username = username,
        email = email,
        password = password,
        confirmPassword = confirmPassword,
      )
    }
  }

  private suspend fun handleRegistration(
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
  ) {
    loadingState.update { true }
    errorState.update { emptyList() }

    if (password != confirmPassword) {
      errorState.update {
        listOf(
          RegisterContract.RegistrationProblem.FieldValidation(
            listOf(RegisterContract.Field.Password),
          ),
        )
      }
      loadingState.update { false }
      return
    }

    when (val result = authRepository.register(email, password, username)) {
      is LoginResult.Success -> {
        errorState.update { emptyList() }
        mutableNavigationState.update { RegisterContract.NavigationState.Close }
      }
      is LoginResult.Error -> errorState.update { result.toUiProblems() }
    }
    loadingState.update { false }
  }

  private fun LoginResult.Error.toUiProblems(): List<RegisterContract.RegistrationProblem> {
    val normalizedMessage = message.lowercase()
    val fields = buildList {
      when {
        normalizedMessage.contains(
          "email",
        ) &&
          normalizedMessage.contains("exist") -> add(RegisterContract.Field.Email)
        normalizedMessage.contains("username") &&
          (
            normalizedMessage.contains(
              "taken",
            ) ||
              normalizedMessage.contains("exist")
            ) -> add(RegisterContract.Field.Username)
        normalizedMessage.contains("email") -> add(RegisterContract.Field.EmailFormat)
        normalizedMessage.contains("username") -> add(RegisterContract.Field.UsernameFormat)
        normalizedMessage.contains("password") -> add(RegisterContract.Field.Password)
        statusCode == 422 -> add(RegisterContract.Field.Unknown)
      }
    }
    return if (fields.isNotEmpty()) {
      listOf(RegisterContract.RegistrationProblem.FieldValidation(fields))
    } else {
      listOf(RegisterContract.RegistrationProblem.GenericError)
    }
  }

  private companion object {
    const val STOP_TIMEOUT_MILLIS = 5_000L
  }
}
