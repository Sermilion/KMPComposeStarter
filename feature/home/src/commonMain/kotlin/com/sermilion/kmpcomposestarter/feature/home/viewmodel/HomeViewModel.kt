package com.sermilion.kmpcomposestarter.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sermilion.kmpcomposestarter.common.coroutines.Effect
import com.sermilion.kmpcomposestarter.common.di.ContributesViewModel
import com.sermilion.kmpcomposestarter.common.di.ScreenScope
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
@ContributesViewModel(ScreenScope::class)
class HomeViewModel(private val userData: UserData) : ViewModel() {

  private val _uiState = MutableStateFlow(
    HomeContract.UiState(
      userName = userData.name,
      userEmail = userData.email,
    ),
  )
  val uiState: StateFlow<HomeContract.UiState> = _uiState.asStateFlow()

  private val _effects = Effect<HomeContract.Event>()
  val effects: Flow<HomeContract.Event> = _effects.flow

  fun navigateToProfile() {
    viewModelScope.launch {
      _effects.emit(HomeContract.Event.NavigateToProfile)
    }
  }

  /** Sends the id, not the object: the detail screen resolves it from the source of truth. */
  fun openDetail() {
    viewModelScope.launch {
      _effects.emit(HomeContract.Event.NavigateToDetail(userData.id))
    }
  }
}
