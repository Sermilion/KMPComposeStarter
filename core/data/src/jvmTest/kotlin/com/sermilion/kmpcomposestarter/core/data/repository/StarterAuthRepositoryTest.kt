package com.sermilion.kmpcomposestarter.core.data.repository

import com.sermilion.kmpcomposestarter.core.data.api.AuthApiService
import com.sermilion.kmpcomposestarter.core.data.api.AuthResponse
import com.sermilion.kmpcomposestarter.core.data.di.FakeUserComponent
import com.sermilion.kmpcomposestarter.core.data.di.RecordingUserComponentFactory
import com.sermilion.kmpcomposestarter.core.data.di.StarterUserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.auth.AuthToken
import com.sermilion.kmpcomposestarter.core.domain.model.LoginResult
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest

private val testUser = UserData("id-1", "user@test.com", "User")
private val testToken = AuthToken(accessToken = "access-token")

private class StubAuthApiService(private val logoutFailure: Throwable? = null) : AuthApiService {
  override suspend fun login(email: String, password: String): AuthResponse =
    AuthResponse.Success(testUser, testToken)

  override suspend fun register(email: String, password: String, name: String): AuthResponse =
    AuthResponse.Success(testUser, testToken)

  override suspend fun logout() {
    logoutFailure?.let { throw it }
  }
}

class StarterAuthRepositoryTest :
  FunSpec({

    test("login opens a session and stores the token in that session") {
      runTest {
        val manager = StarterUserComponentManager(RecordingUserComponentFactory())
        val repository = StarterAuthRepository(
          authApiService = StubAuthApiService(),
          userComponentManager = manager,
          externalScope = backgroundScope,
        )

        repository.login("user@test.com", "password") shouldBe LoginResult.Success(testUser)

        manager.userComponent?.userData shouldBe testUser
        manager.userComponent?.tokenStore?.get() shouldBe testToken
      }
    }

    test("a failing remote sign-out still tears down the session and clears the token") {
      runTest {
        val manager = StarterUserComponentManager(RecordingUserComponentFactory())
        val repository = StarterAuthRepository(
          authApiService = StubAuthApiService(logoutFailure = IllegalStateException("offline")),
          userComponentManager = manager,
          externalScope = backgroundScope,
        )

        repository.login("user@test.com", "password")
        val session = manager.userComponent as FakeUserComponent

        shouldThrow<IllegalStateException> { repository.logout() }

        manager.userComponent shouldBe null
        session.closeCount shouldBe 1
        session.tokenStoreImpl.get() shouldBe null
      }
    }
  })
