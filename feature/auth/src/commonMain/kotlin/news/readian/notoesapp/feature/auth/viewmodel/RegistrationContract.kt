
package news.readian.notoesapp.feature.auth.viewmodel

object RegistrationContract {
  sealed interface UiState {
    data class Content(
      val loading: Boolean = false,
      val errors: List<RegistrationProblem> = listOf(),
    ) : UiState

    data object Initial : UiState
  }

  sealed interface RegistrationProblem {
    data class FieldValidation(val fields: List<Field>) : RegistrationProblem
    data object GenericError : RegistrationProblem
  }

  sealed interface NavigationState {
    data object Registration : NavigationState
    data object Close : NavigationState
  }

  enum class Field {
    Email,
    EmailFormat,
    Username,
    UsernameFormat,
    Password,
    Unknown,
  }
}
