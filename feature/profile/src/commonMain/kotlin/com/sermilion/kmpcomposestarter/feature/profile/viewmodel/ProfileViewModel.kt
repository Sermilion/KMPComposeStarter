package com.sermilion.kmpcomposestarter.feature.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.common.coroutines.Effect
import com.sermilion.kmpcomposestarter.common.di.ContributesViewModel
import com.sermilion.kmpcomposestarter.common.di.ScreenScope
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.core.domain.repository.AuthRepository
import com.sermilion.kmpcomposestarter.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import kotlin.coroutines.cancellation.CancellationException

@Inject
@ContributesViewModel(ScreenScope::class)
class ProfileViewModel(
  userData: UserData,
  private val authRepository: AuthRepository,
  private val userRepository: UserRepository,
) : ViewModel() {

  private val _uiState = MutableStateFlow(
    ProfileContract.UiState(
      userName = userData.name,
      userEmail = userData.email,
      userId = userData.id,
    ),
  )
  val uiState: StateFlow<ProfileContract.UiState> = _uiState.asStateFlow()

  private val _effects = Effect<ProfileContract.Event>()
  val effects: Flow<ProfileContract.Event> = _effects.flow

  init {
    // The sign-in response seeds the screen so it is never blank, but the stored row is what it
    // follows: a profile edited elsewhere in the session shows up here without a re-login.
    viewModelScope.launch {
      userRepository.observeCurrentUser()
        .catch { error ->
          // Delete-my-data closes this session's database before it touches the files, so a
          // failed deletion leaves the row unreadable for the rest of the session. Stop
          // following it and keep what is already on screen: a stale profile is a better
          // answer than taking the session down with an unhandled read failure.
          Logger.w(TAG, error) { "Stopped following the stored user row." }
        }
        .collect { stored ->
          if (stored != null) {
            _uiState.update {
              it.copy(userName = stored.name, userEmail = stored.email, userId = stored.id)
            }
          }
        }
    }
  }

  fun navigateBack() {
    viewModelScope.launch {
      _effects.emit(ProfileContract.Event.NavigateBack)
    }
  }

  /**
   * Signing out is the whole action: the session flow carries the app back to the auth stack.
   *
   * The `finally` is the point. Without it a repository that throws left `isLoggingOut` stuck at
   * `true`, and the screen stayed busy with every control disabled and no way back out.
   */
  fun logout() {
    if (_uiState.value.isBusy) return

    viewModelScope.launch {
      _uiState.update { it.copy(isLoggingOut = true, logoutFailed = false) }
      try {
        authRepository.logout()
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        Logger.e(TAG, e) { "Sign-out failed." }
        _uiState.update { it.copy(logoutFailed = true) }
      } finally {
        _uiState.update { it.copy(isLoggingOut = false) }
      }
    }
  }

  /**
   * Erases this user's local database, then signs out — the session's database handle is gone,
   * so staying signed in would leave the screen reading a file that no longer exists.
   *
   * A failed deletion does not sign out. Reporting success while the data is still on disk is
   * the one outcome a delete-my-data control must never produce. The session survives it, but
   * its database handle does not: the screen reports the failure and keeps the values it
   * already has, because nothing in this session can read that row again.
   */
  fun deleteMyData() {
    if (_uiState.value.isBusy) return

    viewModelScope.launch {
      _uiState.update { it.copy(isDeletingData = true, dataDeletionFailed = false) }
      if (userRepository.deleteMyData()) {
        authRepository.logout()
      } else {
        _uiState.update { it.copy(isDeletingData = false, dataDeletionFailed = true) }
      }
    }
  }

  private companion object {
    const val TAG = "ProfileViewModel"
  }
}
