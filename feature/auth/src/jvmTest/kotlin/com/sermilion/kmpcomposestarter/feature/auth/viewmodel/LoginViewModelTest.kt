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

private val demoCredentials = DemoCredentials(
  loginEmail = "demo@example.com",
  password = "demo-password",
  newUserEmail = "new@example.com",
  newUserName = "New User",
)

private fun viewModelWith(authRepository: AuthRepository) =
  LoginViewModel(authRepository, demoCredentials)

class LoginViewModelTest :
  FunSpec({

    val testDispatcher = StandardTestDispatcher()

    beforeEach { Dispatchers.setMain(testDispatcher) }
    afterEach { Dispatchers.resetMain() }

    test("editing a field clears the error the previous attempt left behind") {
      runTest(testDispatcher) {
        val authRepository = mockk<AuthRepository>()
        coEvery { authRepository.login(any(), any()) } returns
          LoginResult.Failure(AuthError.InvalidCredentials)

        val viewModel = viewModelWith(authRepository)
        viewModel.onEmailChange("test@email.com")
        viewModel.onPasswordChange("wrong")
        viewModel.login()
        advanceUntilIdle()

        viewModel.uiState.value.error shouldBe LoginContract.Error.InvalidCredentials

        viewModel.onEmailChange("new@email.com")

        viewModel.uiState.value.error shouldBe null
      }
    }

    test("a successful login clears the spinner and emits no navigation effect") {
      runTest(testDispatcher) {
        val userData = UserData(id = "123", email = "test@email.com", name = "Test User")
        val authRepository = mockk<AuthRepository>()
        coEvery { authRepository.login("test@email.com", "password123") } returns
          LoginResult.Success(userData)

        val viewModel = viewModelWith(authRepository)
        viewModel.onEmailChange("test@email.com")
        viewModel.onPasswordChange("password123")
        viewModel.login()
        advanceUntilIdle()

        viewModel.uiState.value.isLoading shouldBe false
        viewModel.uiState.value.error shouldBe null
        // The session flow the repository writes is the only thing that moves the app to the
        // signed-in shell. A second, screen-driven signal would race it.
        viewModel.effects.test { expectNoEvents() }
        coVerify(exactly = 1) { authRepository.login("test@email.com", "password123") }
      }
    }

    test("each failure reason surfaces as its own error, not one generic message") {
      val expected = mapOf(
        AuthError.InvalidCredentials to LoginContract.Error.InvalidCredentials,
        AuthError.Network to LoginContract.Error.Network,
        AuthError.RefreshFailed to LoginContract.Error.Unknown,
        AuthError.Unexpected(cause = null) to LoginContract.Error.Unknown,
      )

      runTest(testDispatcher) {
        expected.forEach { (repositoryError, uiError) ->
          val authRepository = mockk<AuthRepository>()
          coEvery { authRepository.login(any(), any()) } returns
            LoginResult.Failure(repositoryError)

          val viewModel = viewModelWith(authRepository)
          viewModel.onEmailChange("test@email.com")
          viewModel.onPasswordChange("password")
          viewModel.login()
          advanceUntilIdle()

          viewModel.uiState.value.error shouldBe uiError
          viewModel.uiState.value.isLoading shouldBe false
        }
      }
    }

    test("a second tap while a login is in flight does not reach the repository") {
      runTest(testDispatcher) {
        val authRepository = mockk<AuthRepository>()
        coEvery { authRepository.login(any(), any()) } coAnswers {
          delay(1000)
          LoginResult.Success(UserData("1", "test@email.com", "Test"))
        }

        val viewModel = viewModelWith(authRepository)
        viewModel.onEmailChange("test@email.com")
        viewModel.onPasswordChange("password")

        viewModel.login()
        testDispatcher.scheduler.runCurrent()
        viewModel.uiState.value.isLoading shouldBe true

        viewModel.login()
        advanceUntilIdle()

        coVerify(exactly = 1) { authRepository.login(any(), any()) }
        viewModel.uiState.value.isLoading shouldBe false
      }
    }

    test("loginDemo uses the injected demo credentials") {
      runTest(testDispatcher) {
        val userData = UserData(id = "123", email = demoCredentials.loginEmail, name = "Test User")
        val authRepository = mockk<AuthRepository>()
        coEvery {
          authRepository.login(demoCredentials.loginEmail, demoCredentials.password)
        } returns LoginResult.Success(userData)

        val viewModel = viewModelWith(authRepository)
        viewModel.loginDemo()
        advanceUntilIdle()

        viewModel.uiState.value.email shouldBe demoCredentials.loginEmail
        viewModel.uiState.value.password shouldBe demoCredentials.password
        coVerify(exactly = 1) {
          authRepository.login(demoCredentials.loginEmail, demoCredentials.password)
        }
      }
    }

    test("navigateToRegister emits NavigateToRegister") {
      runTest(testDispatcher) {
        val viewModel = viewModelWith(mockk())

        viewModel.navigateToRegister()
        advanceUntilIdle()

        viewModel.effects.test {
          awaitItem() shouldBe LoginContract.Event.NavigateToRegister
          cancelAndIgnoreRemainingEvents()
        }
      }
    }
  })
