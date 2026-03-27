
package news.readian.notoesapp.feature.auth.viewmodel

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import news.readian.notoesapp.core.data.repository.AuthRepository
import news.readian.notoesapp.core.data.repository.LoginResult
import news.readian.notoesapp.core.domain.model.UserData

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest :
  FunSpec({
    val testDispatcher = StandardTestDispatcher()

    beforeEach {
      Dispatchers.setMain(testDispatcher)
    }

    afterEach {
      Dispatchers.resetMain()
    }

    test("initial state should be idle") {
      val authRepository = mockk<AuthRepository>()
      val viewModel = LoginViewModel(authRepository)

      viewModel.uiState.value.loading shouldBe false
      viewModel.uiState.value.errors shouldBe emptyList()
    }

    test("successful login clears errors") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login("test@email.com", "password123") } returns LoginResult.Success(
        UserData(id = "1", email = "test@email.com", name = "Test", token = "token"),
      )
      val viewModel = LoginViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onLogin("test@email.com", "password123")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value.loading shouldBe false
      viewModel.uiState.value.errors shouldBe emptyList()
      coVerify { authRepository.login("test@email.com", "password123") }
    }

    test("invalid credentials map to invalid-credentials problem") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } returns LoginResult.Error(
        message = "Invalid credentials",
        statusCode = 401,
      )
      val viewModel = LoginViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onLogin("wrong@email.com", "wrong")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value.errors shouldBe listOf(LoginContract.LoginProblem.InvalidCredentials)
    }

    test("credential message fallback maps to invalid-credentials problem") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } returns LoginResult.Error(
        message = "Credential check failed",
      )
      val viewModel = LoginViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onLogin("wrong", "wrong")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value.errors shouldBe listOf(LoginContract.LoginProblem.InvalidCredentials)
    }

    test("rate limited response maps to rate-limited problem") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } returns LoginResult.Error(
        message = "Too many attempts",
        statusCode = 429,
      )
      val viewModel = LoginViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onLogin("test@email.com", "password123")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value.errors shouldBe listOf(LoginContract.LoginProblem.RateLimited)
    }

    test("server error response maps to server-error problem") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } returns LoginResult.Error(
        message = "Internal server error",
        statusCode = 500,
      )
      val viewModel = LoginViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onLogin("test@email.com", "password123")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value.errors shouldBe listOf(LoginContract.LoginProblem.ServerError)
    }

    test("timeout response maps to network-timeout problem") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } returns LoginResult.Error(
        message = "Request timed out",
      )
      val viewModel = LoginViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onLogin("test@email.com", "password123")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value.errors shouldBe listOf(LoginContract.LoginProblem.NetworkTimeout)
    }

    test("validation response maps to unknown-field validation problem") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } returns LoginResult.Error(
        message = "Validation failed",
        statusCode = 422,
      )
      val viewModel = LoginViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onLogin("test@email.com", "password123")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value.errors shouldBe listOf(
        LoginContract.LoginProblem.Validation(listOf(LoginContract.Field.Unknown)),
      )
    }

    test("unexpected error maps to generic problem") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } returns LoginResult.Error(
        message = "Something odd happened",
        statusCode = 400,
      )
      val viewModel = LoginViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onLogin("test@email.com", "password123")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value.errors shouldBe listOf(LoginContract.LoginProblem.GenericError)
    }
  })
