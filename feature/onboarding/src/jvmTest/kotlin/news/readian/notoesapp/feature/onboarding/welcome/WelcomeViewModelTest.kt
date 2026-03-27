package news.readian.notoesapp.feature.onboarding.welcome

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import news.readian.notoesapp.common.coroutines.DispatcherProvider
import news.readian.notoesapp.core.data.repository.AuthRepository
import news.readian.notoesapp.core.data.repository.LoginResult
import news.readian.notoesapp.core.domain.model.UserData
import news.readian.notoesapp.feature.onboarding.common.ui.model.AnonRegProblem

@OptIn(ExperimentalCoroutinesApi::class)
class WelcomeViewModelTest :
  FunSpec({
    val testDispatcher = StandardTestDispatcher()

    beforeEach {
      Dispatchers.setMain(testDispatcher)
    }

    afterEach {
      Dispatchers.resetMain()
    }

    test("successful guest login clears welcome errors") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.loginGuest() } returns LoginResult.Success(
        UserData(id = "1", email = "guest@email.com", name = "Guest", token = "token"),
      )
      val viewModel =
        WelcomeViewModel(authRepository, WelcomeTestDispatcherProvider(testDispatcher))

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onSkipClick()
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value shouldBe WelcomeContract.UiState(
        loading = false,
        errors = emptyList(),
      )
    }

    test("failed guest login shows generic welcome error") {
      val authRepository = mockk<AuthRepository>()
      coEvery { authRepository.loginGuest() } returns LoginResult.Error(
        message = "Unable to continue as guest",
      )
      val viewModel =
        WelcomeViewModel(authRepository, WelcomeTestDispatcherProvider(testDispatcher))

      runTest(testDispatcher) {
        val uiStateCollector = backgroundScope.launch {
          viewModel.uiState.collect {}
        }
        viewModel.onSkipClick()
        advanceUntilIdle()
        uiStateCollector.cancel()
      }

      viewModel.uiState.value shouldBe WelcomeContract.UiState(
        loading = false,
        errors = listOf(AnonRegProblem.GenericError),
      )
    }
  })

private class WelcomeTestDispatcherProvider(private val dispatcher: CoroutineDispatcher) :
  DispatcherProvider {
  override val io: CoroutineDispatcher = dispatcher
  override val main: CoroutineDispatcher = dispatcher
  override val default: CoroutineDispatcher = dispatcher
}
