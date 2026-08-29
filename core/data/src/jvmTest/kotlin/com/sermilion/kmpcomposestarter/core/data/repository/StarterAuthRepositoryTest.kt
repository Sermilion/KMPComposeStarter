package com.sermilion.kmpcomposestarter.core.data.repository

import com.sermilion.kmpcomposestarter.core.data.di.FakeUserComponent
import com.sermilion.kmpcomposestarter.core.data.di.RecordingUserComponentFactory
import com.sermilion.kmpcomposestarter.core.data.di.StarterUserComponentManager
import com.sermilion.kmpcomposestarter.core.data.model.AuthResultDataModel
import com.sermilion.kmpcomposestarter.core.data.model.AuthTokenDataModel
import com.sermilion.kmpcomposestarter.core.data.model.UserDataModel
import com.sermilion.kmpcomposestarter.core.data.remote.AuthRemoteDataSource
import com.sermilion.kmpcomposestarter.core.domain.auth.AuthToken
import com.sermilion.kmpcomposestarter.core.domain.model.LoginResult
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest

private val testUserModel = UserDataModel("id-1", "user@test.com", "User")
private val testUser = UserData("id-1", "user@test.com", "User")
private val testToken = AuthToken(accessToken = "access-token")

private class StubAuthRemoteDataSource(private val logoutFailure: Throwable? = null) :
  AuthRemoteDataSource {

  override suspend fun login(email: String, password: String): AuthResultDataModel =
    AuthResultDataModel.Success(testUserModel, AuthTokenDataModel(testToken.accessToken))

  override suspend fun register(
    email: String,
    password: String,
    name: String,
  ): AuthResultDataModel =
    AuthResultDataModel.Success(testUserModel, AuthTokenDataModel(testToken.accessToken))

  override suspend fun logout() {
    logoutFailure?.let { throw it }
  }
}

class StarterAuthRepositoryTest :
  FunSpec({

    test("login opens a session, then stores the token and the user row inside it") {
      runTest {
        val manager = StarterUserComponentManager(RecordingUserComponentFactory())
        val repository = StarterAuthRepository(
          remoteDataSource = StubAuthRemoteDataSource(),
          userComponentManager = manager,
          externalScope = backgroundScope,
        )

        repository.login("user@test.com", "password") shouldBe LoginResult.Success(testUser)

        val session = manager.userComponent as FakeUserComponent
        session.userData shouldBe testUser
        session.tokenStore.get() shouldBe testToken
        // Writing the row through the session's repository is what keeps it out of the previous
        // user's database: an app-scoped write would land wherever the last session pointed.
        session.userRepositoryImpl.savedUsers shouldBe listOf(testUser)
      }
    }

    test("a failing remote sign-out still tears down the session and clears the token") {
      runTest {
        val manager = StarterUserComponentManager(RecordingUserComponentFactory())
        val repository = StarterAuthRepository(
          remoteDataSource = StubAuthRemoteDataSource(IllegalStateException("offline")),
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
