package com.sermilion.kmpcomposestarter.core.data.session

import com.sermilion.kmpcomposestarter.core.data.di.RecordingUserComponentFactory
import com.sermilion.kmpcomposestarter.core.data.di.StarterUserComponentManager
import com.sermilion.kmpcomposestarter.core.data.local.AuthLocalDataSource
import com.sermilion.kmpcomposestarter.core.data.model.AuthTokenDataModel
import com.sermilion.kmpcomposestarter.core.data.model.StoredSession
import com.sermilion.kmpcomposestarter.core.data.model.UserDataModel
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.core.domain.session.SessionState
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest

private val storedSession =
  StoredSession(
    user = UserDataModel("id-1", "user@test.com", "User"),
    token = AuthTokenDataModel("access-token"),
  )

private class StubAuthLocalDataSource(
  private val session: StoredSession?,
  private val readFailure: Throwable? = null,
) : AuthLocalDataSource {
  override suspend fun getSession(): StoredSession? {
    readFailure?.let { throw it }
    return session
  }

  override suspend fun saveSession(session: StoredSession) = Unit

  override suspend fun clearSession(userId: String) = Unit
}

@OptIn(ExperimentalCoroutinesApi::class)
class StarterSessionRestorerTest :
  FunSpec({

    test("a returning user is never published as unauthenticated on the way to their session") {
      runTest {
        val manager = StarterUserComponentManager(RecordingUserComponentFactory())
        val restorer = StarterSessionRestorer(StubAuthLocalDataSource(storedSession), manager)

        val observed = mutableListOf<Pair<SessionState, Boolean>>()
        val collector =
          backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            restorer.state.collect { observed += it to (manager.userComponent != null) }
          }

        restorer.restore()

        observed shouldBe
          listOf(
            SessionState.Loading to false,
            SessionState.Authenticated to true,
          )
        manager.userComponent?.userData shouldBe UserData("id-1", "user@test.com", "User")

        collector.cancel()
      }
    }

    test("no stored session ends signed out without opening one") {
      runTest {
        val factory = RecordingUserComponentFactory()
        val manager = StarterUserComponentManager(factory)
        val restorer = StarterSessionRestorer(StubAuthLocalDataSource(session = null), manager)

        restorer.restore()

        restorer.state.value shouldBe SessionState.Unauthenticated
        factory.created shouldHaveSize 0
      }
    }

    test("an unreadable store ends signed out instead of stranding the shell on Loading") {
      runTest {
        val manager = StarterUserComponentManager(RecordingUserComponentFactory())
        val restorer =
          StarterSessionRestorer(
            StubAuthLocalDataSource(session = null, readFailure = IllegalStateException("corrupt")),
            manager,
          )

        restorer.restore()

        restorer.state.value shouldBe SessionState.Unauthenticated
      }
    }

    test("restoring twice does not rebuild the session") {
      runTest {
        val factory = RecordingUserComponentFactory()
        val manager = StarterUserComponentManager(factory)
        val restorer = StarterSessionRestorer(StubAuthLocalDataSource(storedSession), manager)

        restorer.restore()
        restorer.restore()

        factory.created shouldHaveSize 1
      }
    }
  })
