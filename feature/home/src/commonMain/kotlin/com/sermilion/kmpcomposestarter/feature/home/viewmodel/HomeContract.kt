package com.sermilion.kmpcomposestarter.feature.home.viewmodel

object HomeContract {
  data class UiState(
    val userName: String = "",
    val userEmail: String = "",
  )

  sealed interface Event {
    data object NavigateToProfile : Event

    /** Carries the id alone; the detail screen resolves it. */
    data class NavigateToDetail(
      val id: String,
    ) : Event
  }
}
