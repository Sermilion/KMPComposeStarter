package com.sermilion.kmpcomposestarter.feature.home.viewmodel

import app.cash.turbine.test
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

private val signedInUser = UserData(id = "user-1", email = "ada@example.com", name = "Ada Lovelace")

class HomeViewModelTest :
  FunSpec({

    val testDispatcher = StandardTestDispatcher()

    beforeEach { Dispatchers.setMain(testDispatcher) }
    afterEach { Dispatchers.resetMain() }

    /**
     * The regression guard for the effect migration. A zero-replay `MutableSharedFlow` dropped
     * anything emitted while the screen had no collector attached, so a profile tap made during
     * recomposition left the user sitting on Home with nothing to show for it. The buffered
     * channel has to hold the effect until someone reads it.
     */
    test("an effect emitted before any collector attaches is still delivered") {
      runTest(testDispatcher) {
        val viewModel = HomeViewModel(signedInUser)

        viewModel.navigateToProfile()
        advanceUntilIdle()

        viewModel.effects.test {
          awaitItem() shouldBe HomeContract.Event.NavigateToProfile
          cancelAndIgnoreRemainingEvents()
        }
      }
    }

    test("openDetail carries the user id") {
      runTest(testDispatcher) {
        val viewModel = HomeViewModel(signedInUser)

        viewModel.openDetail()
        advanceUntilIdle()

        viewModel.effects.test {
          awaitItem() shouldBe HomeContract.Event.NavigateToDetail(signedInUser.id)
          cancelAndIgnoreRemainingEvents()
        }
      }
    }
  })
