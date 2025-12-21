package com.sermilion.kmpcomposestarter.feature.home.viewmodel

object HomeContract {

  data class UiState(
    val userName: String = "",
    val userEmail: String = "",
    val isLoading: Boolean = false,
  )

  sealed interface Event {
    data object NavigateToProfile : Event
  }
}
