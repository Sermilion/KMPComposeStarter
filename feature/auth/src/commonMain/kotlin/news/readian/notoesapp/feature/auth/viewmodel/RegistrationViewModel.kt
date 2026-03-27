
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
import news.readian.notoesapp.feature.auth.viewmodel.RegistrationContract.NavigationState
import kotlin.time.Duration.Companion.seconds

@Inject
class RegistrationViewModel(private val authRepository: AuthRepository) : ViewModel() {

  private val loadingState = MutableStateFlow(false)
  private val errorState = MutableStateFlow(listOf<RegistrationContract.RegistrationProblem>())
  private val navigationStateFlow = MutableStateFlow<NavigationState>(NavigationState.Registration)

  val navigationState = navigationStateFlow.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
    initialValue = NavigationState.Registration,
  )

  val uiState = combine(loadingState, errorState) { loading, errors ->
    RegistrationContract.UiState.Content(loading = loading, errors = errors)
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
    initialValue = RegistrationContract.UiState.Initial,
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
    errorState.update { listOf() }

    if (password != confirmPassword) {
      errorState.update {
        listOf(
          RegistrationContract.RegistrationProblem.FieldValidation(
            listOf(RegistrationContract.Field.Password),
          ),
        )
      }
      loadingState.update { false }
      return
    }

    when (val result = authRepository.register(email, password, username)) {
      is LoginResult.Success -> errorState.update { listOf() }
      is LoginResult.Error -> errorState.update { result.toUiProblems() }
    }
    loadingState.update { false }
  }

  private fun LoginResult.Error.toUiProblems(): List<RegistrationContract.RegistrationProblem> {
    val normalizedMessage = message.lowercase()
    val fields = buildList {
      when {
        normalizedMessage.contains(
          "email",
        ) &&
          normalizedMessage.contains("exist") -> add(RegistrationContract.Field.Email)
        normalizedMessage.contains("username") &&
          (
            normalizedMessage.contains(
              "taken",
            ) ||
              normalizedMessage.contains("exist")
            ) -> add(RegistrationContract.Field.Username)
        normalizedMessage.contains("email") -> add(RegistrationContract.Field.EmailFormat)
        normalizedMessage.contains("username") -> add(RegistrationContract.Field.UsernameFormat)
        normalizedMessage.contains("password") -> add(RegistrationContract.Field.Password)
        statusCode == 422 -> add(RegistrationContract.Field.Unknown)
      }
    }
    return if (fields.isNotEmpty()) {
      listOf(RegistrationContract.RegistrationProblem.FieldValidation(fields))
    } else {
      listOf(RegistrationContract.RegistrationProblem.GenericError)
    }
  }
}
