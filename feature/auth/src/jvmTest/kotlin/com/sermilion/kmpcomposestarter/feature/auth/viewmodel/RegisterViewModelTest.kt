package com.sermilion.kmpcomposestarter.feature.auth.viewmodel

import app.cash.turbine.test
import com.sermilion.kmpcomposestarter.core.domain.model.AuthError
import com.sermilion.kmpcomposestarter.core.domain.model.DemoCredentials
import com.sermilion.kmpcomposestarter.core.domain.model.LoginResult
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.core.domain.repository.AuthRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

/** Virtual time the faked call stays in flight, long enough for the second tap to land. */
private const val IN_FLIGHT_MILLIS = 1_000L

private val demoCredentials =
  DemoCredentials(
    loginEmail = "demo@example.com",
    password = "demo-password",
    newUserEmail = "new@example.com",
    newUserName = "New User",
  )

private fun viewModelWith(authRepository: AuthRepository) =
  RegisterViewModel(authRepository, demoCredentials)

private fun RegisterViewModel.fillForm() {
  onNameChange("Ada Lovelace")
  onEmailChange("ada@example.com")
  onPasswordChange("correct-horse")
}

class RegisterViewModelTest :
  FunSpec({

    val testDispatcher = StandardTestDispatcher()

    beforeEach { Dispatchers.setMain(testDispatcher) }
    afterEach { Dispatchers.resetMain() }

    test("a second register while one is in flight does not reach the repository") {
      runTest(testDispatcher) {
        val authRepository = mockk<AuthRepository>()
        coEvery { authRepository.register(any(), any(), any()) } coAnswers {
          delay(IN_FLIGHT_MILLIS)
          LoginResult.Success(UserData("1", "ada@example.com", "Ada Lovelace"))
        }

        val viewModel = viewModelWith(authRepository)
        viewModel.fillForm()

        viewModel.register()
        testDispatcher.scheduler.runCurrent()
        viewModel.uiState.value.isLoading shouldBe true

        viewModel.register()
        advanceUntilIdle()

        coVerify(exactly = 1) { authRepository.register(any(), any(), any()) }
        viewModel.uiState.value.isLoading shouldBe false
      }
    }

    test("each failure reason maps to its own error variant") {
      val expected =
        mapOf(
          AuthError.InvalidCredentials to RegisterContract.Error.RegistrationFailed,
          AuthError.Network to RegisterContract.Error.Network,
          AuthError.RefreshFailed to RegisterContract.Error.Unknown,
          AuthError.Unexpected(cause = null) to RegisterContract.Error.Unknown,
        )

      runTest(testDispatcher) {
        expected.forEach { (repositoryError, uiError) ->
          val authRepository = mockk<AuthRepository>()
          coEvery { authRepository.register(any(), any(), any()) } returns
            LoginResult.Failure(repositoryError)

          val viewModel = viewModelWith(authRepository)
          viewModel.fillForm()
          viewModel.register()
          advanceUntilIdle()

          viewModel.uiState.value.error shouldBe uiError
          viewModel.uiState.value.isLoading shouldBe false
        }
      }
    }

    test("navigateBack emits NavigateBack") {
      runTest(testDispatcher) {
        val viewModel = viewModelWith(mockk())

        viewModel.navigateBack()
        advanceUntilIdle()

        viewModel.effects.test {
          awaitItem() shouldBe RegisterContract.Event.NavigateBack
          cancelAndIgnoreRemainingEvents()
        }
      }
    }
  })
