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
    data class Unknown(val message: String) : Error
  }

  sealed interface Event {
    data object LoginSuccess : Event
    data object NavigateToRegister : Event
  }
}
