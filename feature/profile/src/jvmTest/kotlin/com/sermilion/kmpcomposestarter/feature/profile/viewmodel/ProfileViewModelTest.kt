package com.sermilion.kmpcomposestarter.feature.profile.viewmodel

import app.cash.turbine.test
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.core.domain.repository.AuthRepository
import com.sermilion.kmpcomposestarter.core.domain.repository.UserRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

private val signedInUser = UserData(id = "id-1", email = "user@test.com", name = "User")

class ProfileViewModelTest :
  FunSpec({

    val testDispatcher = StandardTestDispatcher()

    beforeEach { Dispatchers.setMain(testDispatcher) }
    afterEach { Dispatchers.resetMain() }

    test("the profile follows the stored user row, not the sign-in response") {
      runTest(testDispatcher) {
        val userRepository = mockk<UserRepository>()
        every { userRepository.observeCurrentUser() } returns
          flowOf(UserData(id = "id-1", email = "renamed@test.com", name = "Renamed"))

        val viewModel = ProfileViewModel(signedInUser, mockk(), userRepository)
        advanceUntilIdle()

        viewModel.uiState.value.userName shouldBe "Renamed"
        viewModel.uiState.value.userEmail shouldBe "renamed@test.com"
      }
    }

    test("a failed data deletion keeps the user signed in and reports the failure") {
      runTest(testDispatcher) {
        val userRepository = mockk<UserRepository>()
        every { userRepository.observeCurrentUser() } returns emptyFlow()
        coEvery { userRepository.deleteMyData() } returns false
        val authRepository = mockk<AuthRepository>(relaxUnitFun = true)

        val viewModel = ProfileViewModel(signedInUser, authRepository, userRepository)
        viewModel.deleteMyData()
        advanceUntilIdle()

        viewModel.uiState.value.dataDeletionFailed shouldBe true
        viewModel.uiState.value.isDeletingData shouldBe false
        coVerify(exactly = 0) { authRepository.logout() }
      }
    }

    test("a successful data deletion signs the user out") {
      runTest(testDispatcher) {
        val userRepository = mockk<UserRepository>()
        every { userRepository.observeCurrentUser() } returns emptyFlow()
        coEvery { userRepository.deleteMyData() } returns true
        val authRepository = mockk<AuthRepository>(relaxUnitFun = true)

        val viewModel = ProfileViewModel(signedInUser, authRepository, userRepository)
        viewModel.deleteMyData()
        advanceUntilIdle()

        viewModel.uiState.value.dataDeletionFailed shouldBe false
        coVerify(exactly = 1) { authRepository.logout() }
      }
    }

    /**
     * Without the `finally` in `logout`, a repository throw left `isLoggingOut` stuck at `true`:
     * every control on the screen disabled, no message, and no way out short of killing the app.
     */
    test("a logout that throws clears the busy flag and surfaces the failure") {
      runTest(testDispatcher) {
        val userRepository = mockk<UserRepository>()
        every { userRepository.observeCurrentUser() } returns emptyFlow()
        val authRepository = mockk<AuthRepository>()
        coEvery { authRepository.logout() } throws IllegalStateException("session gone")

        val viewModel = ProfileViewModel(signedInUser, authRepository, userRepository)
        viewModel.logout()
        advanceUntilIdle()

        viewModel.uiState.value.isLoggingOut shouldBe false
        viewModel.uiState.value.logoutFailed shouldBe true
        viewModel.uiState.value.isBusy shouldBe false
      }
    }

    test("navigateBack emits NavigateBack") {
      runTest(testDispatcher) {
        val userRepository = mockk<UserRepository>()
        every { userRepository.observeCurrentUser() } returns emptyFlow()

        val viewModel = ProfileViewModel(signedInUser, mockk(), userRepository)
        viewModel.navigateBack()
        advanceUntilIdle()

        viewModel.effects.test {
          awaitItem() shouldBe ProfileContract.Event.NavigateBack
          cancelAndIgnoreRemainingEvents()
        }
      }
    }
  })
