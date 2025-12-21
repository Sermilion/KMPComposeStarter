package com.sermilion.kmpcomposestarter.feature.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sermilion.kmpcomposestarter.common.di.ScreenScope
import com.sermilion.kmpcomposestarter.core.data.repository.AuthRepository
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(ScreenScope::class)
class ProfileViewModel(private val userData: UserData, private val authRepository: AuthRepository) :
  ViewModel() {

  private val _uiState = MutableStateFlow(
    ProfileContract.UiState(
      userName = userData.name,
      userEmail = userData.email,
      userId = userData.id,
    ),
  )
  val uiState: StateFlow<ProfileContract.UiState> = _uiState.asStateFlow()

  private val _events = MutableSharedFlow<ProfileContract.Event>()
  val events: SharedFlow<ProfileContract.Event> = _events.asSharedFlow()

  fun navigateBack() {
    viewModelScope.launch {
      _events.emit(ProfileContract.Event.NavigateBack)
    }
  }

  fun logout() {
    if (_uiState.value.isLoggingOut) return

    viewModelScope.launch {
      _uiState.update { it.copy(isLoggingOut = true) }
      authRepository.logout()
      _events.emit(ProfileContract.Event.LogoutSuccess)
    }
  }
}
