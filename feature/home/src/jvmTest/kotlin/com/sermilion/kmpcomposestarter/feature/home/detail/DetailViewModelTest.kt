package com.sermilion.kmpcomposestarter.feature.home.detail

import androidx.lifecycle.SavedStateHandle
import com.sermilion.kmpcomposestarter.common.di.AssistedArgs
import com.sermilion.kmpcomposestarter.common.di.mapToAssistedArgs
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * Stands in for the generated `DetailViewModel_Entry`, whose whole job is to read the constructor
 * arguments back out of [AssistedArgs] under the constructor's own parameter names.
 *
 * Constructing the ViewModel through those keys is the point: the key contract between
 * `injectViewModel(assisted = ...)`, `StarterViewModelFactory` and the generated entry has drifted
 * before, and a mismatch renders an empty screen rather than failing.
 */
private fun detailViewModelFrom(args: AssistedArgs): DetailViewModel =
  DetailViewModel(
    id = requireNotNull(args.get<String>("id")),
    savedStateHandle = requireNotNull(args.get<SavedStateHandle>("savedStateHandle")),
  )

class DetailViewModelTest :
  FunSpec({

    test("the route id arrives as an assisted argument and reaches the ui state") {
      val viewModel = detailViewModelFrom(
        mapToAssistedArgs(mapOf("id" to "item-1", "savedStateHandle" to SavedStateHandle())),
      )

      viewModel.uiState.value.id shouldBe "item-1"
    }

    test("the note is written to the SavedStateHandle and read back from it") {
      val handle = SavedStateHandle()

      detailViewModelFrom(mapToAssistedArgs(mapOf("id" to "item-1", "savedStateHandle" to handle)))
        .onNoteChange("remember this")

      // A ViewModel rebuilt against the same handle — what process-death recreation does — must
      // still see the note.
      val recreated = detailViewModelFrom(
        mapToAssistedArgs(mapOf("id" to "item-1", "savedStateHandle" to handle)),
      )

      recreated.uiState.value.note shouldBe "remember this"
    }
  })
