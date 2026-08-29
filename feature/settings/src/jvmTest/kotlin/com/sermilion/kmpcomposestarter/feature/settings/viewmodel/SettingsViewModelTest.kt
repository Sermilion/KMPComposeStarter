package com.sermilion.kmpcomposestarter.feature.settings.viewmodel

import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SettingsViewModelTest :
  FunSpec({

    test("the injected user seeds the screen state") {
      val viewModel =
        SettingsViewModel(
          UserData(id = "user-1", email = "ada@example.com", name = "Ada Lovelace"),
        )

      viewModel.uiState.value shouldBe
        SettingsContract.UiState(userName = "Ada Lovelace", userEmail = "ada@example.com")
    }
  })
