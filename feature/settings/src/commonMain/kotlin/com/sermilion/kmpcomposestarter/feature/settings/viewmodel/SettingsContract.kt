package com.sermilion.kmpcomposestarter.feature.settings.viewmodel

object SettingsContract {
  data class UiState(
    val userName: String = "",
    val userEmail: String = "",
  )
}
