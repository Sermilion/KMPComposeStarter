package com.sermilion.kmpcomposestarter.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import com.sermilion.kmpcomposestarter.common.di.ScreenScope
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(ScreenScope::class)
class SettingsViewModel(private val userData: UserData) : ViewModel() {

  private val _uiState = MutableStateFlow(
    SettingsContract.UiState(
      userName = userData.name,
      userEmail = userData.email,
    ),
  )
  val uiState: StateFlow<SettingsContract.UiState> = _uiState.asStateFlow()
}
