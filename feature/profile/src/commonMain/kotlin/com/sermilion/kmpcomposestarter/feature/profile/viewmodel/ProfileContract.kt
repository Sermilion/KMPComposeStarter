package com.sermilion.kmpcomposestarter.feature.profile.viewmodel

object ProfileContract {

  data class UiState(
    val userName: String = "",
    val userEmail: String = "",
    val userId: String = "",
    val isLoggingOut: Boolean = false,
  )

  sealed interface Event {
    data object NavigateBack : Event
    data object LogoutSuccess : Event
  }
}
