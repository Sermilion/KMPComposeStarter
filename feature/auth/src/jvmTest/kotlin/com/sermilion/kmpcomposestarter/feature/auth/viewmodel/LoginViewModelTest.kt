package com.sermilion.kmpcomposestarter.feature.auth.viewmodel

import com.sermilion.kmpcomposestarter.core.data.config.MockConfig
import com.sermilion.kmpcomposestarter.core.data.repository.AuthRepository
import com.sermilion.kmpcomposestarter.core.data.repository.LoginResult
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
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
      val authRepository = mockk<AuthRepository>()
      val viewModel = LoginViewModel(authRepository)

      val state = viewModel.uiState.value

      state.email shouldBe ""
      state.password shouldBe ""
      state.isLoading shouldBe false
      state.error shouldBe null
    }

    test("onEmailChange updates email in state") {
      val authRepository = mockk<AuthRepository>()
      val viewModel = LoginViewModel(authRepository)

      viewModel.onEmailChange("test@email.com")

      viewModel.uiState.value.email shouldBe "test@email.com"
    }

    test("onPasswordChange updates password in state") {
      val authRepository = mockk<AuthRepository>()
      val viewModel = LoginViewModel(authRepository)

      viewModel.onPasswordChange("secret123")

      viewModel.uiState.value.password shouldBe "secret123"
    }

    test("onEmailChange clears error") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } returns LoginResult.Error("Invalid")

      val viewModel = LoginViewModel(authRepository)
      viewModel.onEmailChange("test@email.com")
      viewModel.onPasswordChange("wrong")

      runTest(testDispatcher) {
        viewModel.login()
        advanceUntilIdle()
      }

      viewModel.uiState.value.error shouldBe LoginContract.Error.Unknown("Invalid")

      viewModel.onEmailChange("new@email.com")

      viewModel.uiState.value.error shouldBe null
    }

    test("login with valid credentials emits LoginSuccess event") {
      val userData = UserData(
        id = "123",
        email = "test@email.com",
        name = "Test User",
        token = "token",
      )
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login("test@email.com", "password123") } returns
        LoginResult.Success(userData)

      val viewModel = LoginViewModel(authRepository)
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

    test("login with invalid credentials sets error") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } returns
        LoginResult.Error("Invalid credentials")

      val viewModel = LoginViewModel(authRepository)
      viewModel.onEmailChange("wrong@email.com")
      viewModel.onPasswordChange("wrongpassword")

      runTest(testDispatcher) {
        viewModel.login()
        advanceUntilIdle()
      }

      viewModel.uiState.value.error shouldBe LoginContract.Error.Unknown("Invalid credentials")
      viewModel.uiState.value.isLoading shouldBe false
    }

    test("login sets isLoading true during request") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.login(any(), any()) } coAnswers {
        kotlinx.coroutines.delay(100)
        LoginResult.Success(UserData("1", "test@email.com", "Test", "token"))
      }

      val viewModel = LoginViewModel(authRepository)
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
        LoginResult.Success(UserData("1", "test@email.com", "Test", "token"))
      }

      val viewModel = LoginViewModel(authRepository)
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

    test("loginDemo sets demo credentials and calls login") {
      val userData = UserData(
        id = "123",
        email = MockConfig.DEMO_EMAIL,
        name = MockConfig.DEMO_USER_NAME,
        token = "token",
      )
      val authRepository = mockk<AuthRepository>()
      coEvery {
        authRepository.login(MockConfig.DEMO_EMAIL, MockConfig.DEMO_PASSWORD)
      } returns LoginResult.Success(userData)

      val viewModel = LoginViewModel(authRepository)

      runTest(testDispatcher) {
        viewModel.loginDemo()
        advanceUntilIdle()
      }

      viewModel.uiState.value.email shouldBe MockConfig.DEMO_EMAIL
      viewModel.uiState.value.password shouldBe MockConfig.DEMO_PASSWORD
      coVerify { authRepository.login(MockConfig.DEMO_EMAIL, MockConfig.DEMO_PASSWORD) }
    }

    test("navigateToRegister emits NavigateToRegister event") {
      val authRepository = mockk<AuthRepository>()
      val viewModel = LoginViewModel(authRepository)

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
