package com.sermilion.kmpcomposestarter.feature.auth.viewmodel

object LoginContract {

  data class UiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: Error? = null,
  )

  sealed interface Error {
    data object InvalidCredentials : Error
    data object Network : Error
    data object Unknown : Error
  }

  sealed interface Event {
    data object NavigateToRegister : Event
  }
}
