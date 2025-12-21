package com.sermilion.kmpcomposestarter.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sermilion.kmpcomposestarter.common.di.ScreenScope
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(ScreenScope::class)
class HomeViewModel(private val userData: UserData) : ViewModel() {

  private val _uiState = MutableStateFlow(
    HomeContract.UiState(
      userName = userData.name,
      userEmail = userData.email,
    ),
  )
  val uiState: StateFlow<HomeContract.UiState> = _uiState.asStateFlow()

  private val _events = MutableSharedFlow<HomeContract.Event>()
  val events: SharedFlow<HomeContract.Event> = _events.asSharedFlow()

  fun navigateToProfile() {
    viewModelScope.launch {
      _events.emit(HomeContract.Event.NavigateToProfile)
    }
  }
}
