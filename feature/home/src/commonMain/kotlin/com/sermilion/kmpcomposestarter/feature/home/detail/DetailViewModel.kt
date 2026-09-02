package com.sermilion.kmpcomposestarter.feature.home.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sermilion.kmpcomposestarter.common.di.ContributesViewModel
import com.sermilion.kmpcomposestarter.common.di.ScreenScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

/**
 * The one consumer of the assisted-args machinery, and the template's answer to "how do I get a
 * navigation argument into a ViewModel?".
 *
 * [id] arrives as a typed constructor parameter — the entry provider publishes it under this
 * parameter's own name — while [savedStateHandle] is filled in by `StarterViewModelFactory`. The
 * KSP processor rejects a `SavedStateHandle` that is not marked [Assisted], so both are.
 */
@Inject
@ContributesViewModel(ScreenScope::class)
class DetailViewModel(
  @Assisted private val id: String,
  @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _uiState =
    MutableStateFlow(
      DetailContract.UiState(id = id, note = savedStateHandle[NOTE_KEY] ?: ""),
    )
  val uiState: StateFlow<DetailContract.UiState> = _uiState.asStateFlow()

  /** The note goes through the handle, so it survives process death and not just recomposition. */
  fun onNoteChange(note: String) {
    savedStateHandle[NOTE_KEY] = note
    _uiState.update { it.copy(note = note) }
  }

  private companion object {
    const val NOTE_KEY = "detail_note"
  }
}
