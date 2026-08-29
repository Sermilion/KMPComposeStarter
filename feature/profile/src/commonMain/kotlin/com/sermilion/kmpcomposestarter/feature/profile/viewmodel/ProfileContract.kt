package com.sermilion.kmpcomposestarter.feature.profile.viewmodel

object ProfileContract {

  data class UiState(
    val userName: String = "",
    val userEmail: String = "",
    val userId: String = "",
    val isLoggingOut: Boolean = false,
    val isDeletingData: Boolean = false,
    val dataDeletionFailed: Boolean = false,
  ) {
    /** One flag for "a destructive action is in flight", so no control can start a second one. */
    val isBusy: Boolean get() = isLoggingOut || isDeletingData
  }

  sealed interface Event {
    data object NavigateBack : Event
    data object LogoutSuccess : Event
  }
}
