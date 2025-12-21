package com.sermilion.kmpcomposestarter.feature.settings.viewmodel

object SettingsContract {

  data class UiState(val userName: String = "", val userEmail: String = "")

  sealed interface Event {
    data object NavigateBack : Event
  }
}
