package com.sermilion.kmpcomposestarter.feature.auth.viewmodel

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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
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

    test("initial state should have empty email and password") {
      val viewModel = viewModelWith(mockk())

      val state = viewModel.uiState.value

      state.email shouldBe ""
      state.password shouldBe ""
      state.isLoading shouldBe false
      state.error shouldBe null
    }

    test("onEmailChange updates email in state") {
      val viewModel = viewModelWith(mockk())

      viewModel.onEmailChange("test@email.com")

      viewModel.uiState.value.email shouldBe "test@email.com"
    }

    test("onPasswordChange updates password in state") {
      val viewModel = viewModelWith(mockk())

      viewModel.onPasswordChange("secret123")

      viewModel.uiState.value.password shouldBe "secret123"
    }

    test("onEmailChange clears error") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } returns
        LoginResult.Failure(AuthError.InvalidCredentials)

      val viewModel = viewModelWith(authRepository)
      viewModel.onEmailChange("test@email.com")
      viewModel.onPasswordChange("wrong")

      runTest(testDispatcher) {
        viewModel.login()
        advanceUntilIdle()
      }

      viewModel.uiState.value.error shouldBe LoginContract.Error.InvalidCredentials

      viewModel.onEmailChange("new@email.com")

      viewModel.uiState.value.error shouldBe null
    }

    test("login with valid credentials emits LoginSuccess event") {
      val userData = UserData(id = "123", email = "test@email.com", name = "Test User")
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login("test@email.com", "password123") } returns
        LoginResult.Success(userData)

      val viewModel = viewModelWith(authRepository)
      viewModel.onEmailChange("test@email.com")
      viewModel.onPasswordChange("password123")

      runTest(testDispatcher) {
        val events = mutableListOf<LoginContract.Event>()
        val job = launch { viewModel.events.collect { events.add(it) } }

        viewModel.login()
        advanceUntilIdle()

        events shouldBe listOf(LoginContract.Event.LoginSuccess)
        viewModel.uiState.value.isLoading shouldBe false

        job.cancel()
      }

      coVerify { authRepository.login("test@email.com", "password123") }
    }

    test("wrong credentials surface the InvalidCredentials reason, not a generic error") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } returns
        LoginResult.Failure(AuthError.InvalidCredentials)

      val viewModel = viewModelWith(authRepository)
      viewModel.onEmailChange("wrong@email.com")
      viewModel.onPasswordChange("wrongpassword")

      runTest(testDispatcher) {
        viewModel.login()
        advanceUntilIdle()
      }

      viewModel.uiState.value.error shouldBe LoginContract.Error.InvalidCredentials
      viewModel.uiState.value.isLoading shouldBe false
    }

    test("a transport failure maps to the Network reason") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } returns LoginResult.Failure(AuthError.Network)

      val viewModel = viewModelWith(authRepository)
      viewModel.onEmailChange("test@email.com")
      viewModel.onPasswordChange("password")

      runTest(testDispatcher) {
        viewModel.login()
        advanceUntilIdle()
      }

      viewModel.uiState.value.error shouldBe LoginContract.Error.Network
    }

    test("login sets isLoading true during request") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } coAnswers {
        kotlinx.coroutines.delay(100)
        LoginResult.Success(UserData("1", "test@email.com", "Test"))
      }

      val viewModel = viewModelWith(authRepository)
      viewModel.onEmailChange("test@email.com")
      viewModel.onPasswordChange("password")

      runTest(testDispatcher) {
        viewModel.login()
        testDispatcher.scheduler.runCurrent()

        viewModel.uiState.value.isLoading shouldBe true

        advanceUntilIdle()

        viewModel.uiState.value.isLoading shouldBe false
      }
    }

    test("login does nothing if already loading") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } coAnswers {
        kotlinx.coroutines.delay(1000)
        LoginResult.Success(UserData("1", "test@email.com", "Test"))
      }

      val viewModel = viewModelWith(authRepository)
      viewModel.onEmailChange("test@email.com")
      viewModel.onPasswordChange("password")

      runTest(testDispatcher) {
        viewModel.login()
        testDispatcher.scheduler.runCurrent()
        viewModel.login()
        advanceUntilIdle()

        coVerify(exactly = 1) { authRepository.login(any(), any()) }
      }
    }

    test("loginDemo uses the injected demo credentials") {
      val userData = UserData(id = "123", email = demoCredentials.loginEmail, name = "Test User")
      val authRepository = mockk<AuthRepository>()
      coEvery {
        authRepository.login(demoCredentials.loginEmail, demoCredentials.password)
      } returns LoginResult.Success(userData)

      val viewModel = viewModelWith(authRepository)

      runTest(testDispatcher) {
        viewModel.loginDemo()
        advanceUntilIdle()
      }

      viewModel.uiState.value.email shouldBe demoCredentials.loginEmail
      viewModel.uiState.value.password shouldBe demoCredentials.password
      coVerify { authRepository.login(demoCredentials.loginEmail, demoCredentials.password) }
    }

    test("navigateToRegister emits NavigateToRegister event") {
      val viewModel = viewModelWith(mockk())

      runTest(testDispatcher) {
        val events = mutableListOf<LoginContract.Event>()
        val job = launch { viewModel.events.collect { events.add(it) } }

        viewModel.navigateToRegister()
        advanceUntilIdle()

        events shouldBe listOf(LoginContract.Event.NavigateToRegister)

        job.cancel()
      }
    }
  })
