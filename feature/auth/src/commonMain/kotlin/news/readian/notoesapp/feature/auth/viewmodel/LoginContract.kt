
package news.readian.notoesapp.feature.auth.viewmodel

object LoginContract {
  data class UiState(val loading: Boolean = false, val errors: List<LoginProblem> = listOf())

  sealed interface LoginProblem {
    data object InvalidCredentials : LoginProblem
    data class Validation(val fields: List<Field>) : LoginProblem
    data object NetworkTimeout : LoginProblem
    data object RateLimited : LoginProblem
    data object ServerError : LoginProblem
    data object GenericError : LoginProblem
  }

  enum class Field {
    Identifier,
    Password,
    Unknown,
  }
}
