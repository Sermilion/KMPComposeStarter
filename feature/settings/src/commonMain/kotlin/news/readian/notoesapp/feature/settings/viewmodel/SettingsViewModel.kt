package news.readian.notoesapp.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import news.readian.notoesapp.common.di.ScreenScope
import news.readian.notoesapp.core.domain.model.UserData
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

  private val _events = MutableSharedFlow<SettingsContract.Event>()
  val events: SharedFlow<SettingsContract.Event> = _events.asSharedFlow()

  fun navigateBack() {
    viewModelScope.launch {
      _events.emit(SettingsContract.Event.NavigateBack)
    }
  }
}
