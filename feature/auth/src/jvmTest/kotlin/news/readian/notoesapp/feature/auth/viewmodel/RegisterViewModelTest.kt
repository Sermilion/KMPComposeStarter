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
class RegisterViewModelTest :
  FunSpec({
    val testDispatcher = StandardTestDispatcher()

    beforeEach {
      Dispatchers.setMain(testDispatcher)
    }

    afterEach {
      Dispatchers.resetMain()
    }

    test("initial state is initial") {
      val authRepository = mockk<AuthRepository>()
      val viewModel = RegisterViewModel(authRepository)

      viewModel.uiState.value shouldBe RegisterContract.UiState.Initial
    }

    test("password mismatch emits password field validation without calling repository") {
      val authRepository = mockk<AuthRepository>()
      val viewModel = RegisterViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onSignUpClick("user", "user@email.com", "Password1!", "Password2!")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value shouldBe RegisterContract.UiState.Content(
        loading = false,
        errors = listOf(
          RegisterContract.RegistrationProblem.FieldValidation(
            listOf(RegisterContract.Field.Password),
          ),
        ),
      )
      coVerify(exactly = 0) { authRepository.register(any(), any(), any()) }
    }

    test("email exists error maps to email field validation") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.register(any(), any(), any()) } returns LoginResult.Error(
        message = "Email already exists",
      )
      val viewModel = RegisterViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onSignUpClick("user", "user@email.com", "Password1!", "Password1!")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value shouldBe RegisterContract.UiState.Content(
        loading = false,
        errors = listOf(
          RegisterContract.RegistrationProblem.FieldValidation(
            listOf(RegisterContract.Field.Email),
          ),
        ),
      )
    }

    test("username taken error maps to username field validation") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.register(any(), any(), any()) } returns LoginResult.Error(
        message = "Username is taken",
      )
      val viewModel = RegisterViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onSignUpClick("user", "user@email.com", "Password1!", "Password1!")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value shouldBe RegisterContract.UiState.Content(
        loading = false,
        errors = listOf(
          RegisterContract.RegistrationProblem.FieldValidation(
            listOf(RegisterContract.Field.Username),
          ),
        ),
      )
    }

    test("unprocessable entity maps to unknown field validation") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.register(any(), any(), any()) } returns LoginResult.Error(
        message = "Validation failed",
        statusCode = 422,
      )
      val viewModel = RegisterViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onSignUpClick("user", "user@email.com", "Password1!", "Password1!")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value shouldBe RegisterContract.UiState.Content(
        loading = false,
        errors = listOf(
          RegisterContract.RegistrationProblem.FieldValidation(
            listOf(RegisterContract.Field.Unknown),
          ),
        ),
      )
    }

    test("generic error maps to generic registration problem") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.register(any(), any(), any()) } returns LoginResult.Error(
        message = "Unexpected failure",
      )
      val viewModel = RegisterViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onSignUpClick("user", "user@email.com", "Password1!", "Password1!")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value shouldBe RegisterContract.UiState.Content(
        loading = false,
        errors = listOf(RegisterContract.RegistrationProblem.GenericError),
      )
    }

    test("successful registration clears errors and calls repository") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.register("user@email.com", "Password1!", "user") } returns
        LoginResult.Success(
          UserData(id = "1", email = "user@email.com", name = "user", token = "token"),
        )
      val viewModel = RegisterViewModel(authRepository)

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onSignUpClick("user", "user@email.com", "Password1!", "Password1!")
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value shouldBe RegisterContract.UiState.Content(
        loading = false,
        errors = emptyList(),
      )
      coVerify { authRepository.register("user@email.com", "Password1!", "user") }
    }
  })
