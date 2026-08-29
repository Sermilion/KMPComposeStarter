package com.sermilion.kmpcomposestarter.feature.profile.viewmodel

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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

private val signedInUser = UserData(id = "id-1", email = "user@test.com", name = "User")

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest :
  FunSpec({

    val testDispatcher = StandardTestDispatcher()

    beforeEach {
      Dispatchers.setMain(testDispatcher)
    }

    afterEach {
      Dispatchers.resetMain()
    }

    test("the profile follows the stored user row, not the sign-in response") {
      val userRepository = mockk<UserRepository>()
      every { userRepository.observeCurrentUser() } returns
        flowOf(UserData(id = "id-1", email = "renamed@test.com", name = "Renamed"))

      val viewModel = ProfileViewModel(signedInUser, mockk(), userRepository)

      runTest(testDispatcher) {
        advanceUntilIdle()
      }

      viewModel.uiState.value.userName shouldBe "Renamed"
      viewModel.uiState.value.userEmail shouldBe "renamed@test.com"
    }

    test("a failed data deletion keeps the user signed in and reports the failure") {
      val userRepository = mockk<UserRepository>()
      every { userRepository.observeCurrentUser() } returns emptyFlow()
      coEvery { userRepository.deleteMyData() } returns false
      val authRepository = mockk<AuthRepository>(relaxUnitFun = true)

      val viewModel = ProfileViewModel(signedInUser, authRepository, userRepository)

      runTest(testDispatcher) {
        viewModel.deleteMyData()
        advanceUntilIdle()
      }

      // Signing out here would tell the user their data is gone while it is still on disk.
      viewModel.uiState.value.dataDeletionFailed shouldBe true
      viewModel.uiState.value.isDeletingData shouldBe false
      coVerify(exactly = 0) { authRepository.logout() }
    }

    test("a successful data deletion signs the user out") {
      val userRepository = mockk<UserRepository>()
      every { userRepository.observeCurrentUser() } returns emptyFlow()
      coEvery { userRepository.deleteMyData() } returns true
      val authRepository = mockk<AuthRepository>(relaxUnitFun = true)

      val viewModel = ProfileViewModel(signedInUser, authRepository, userRepository)

      runTest(testDispatcher) {
        viewModel.deleteMyData()
        advanceUntilIdle()
      }

      viewModel.uiState.value.dataDeletionFailed shouldBe false
      coVerify(exactly = 1) { authRepository.logout() }
    }
  })
