package com.sermilion.kmpcomposestarter.feature.auth.viewmodel

object RegisterContract {

  data class UiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: Error? = null,
  )

  sealed interface Error {
    data object RegistrationFailed : Error
    data object Network : Error
    data object Unknown : Error
  }

  sealed interface Event {
    data object NavigateBack : Event
  }
}
