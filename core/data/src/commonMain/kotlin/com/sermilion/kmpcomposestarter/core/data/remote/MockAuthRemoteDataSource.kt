package com.sermilion.kmpcomposestarter.core.data.remote

import com.sermilion.kmpcomposestarter.core.data.config.MockConfig
import com.sermilion.kmpcomposestarter.core.data.model.AuthResultDataModel
import com.sermilion.kmpcomposestarter.core.data.model.AuthTokenDataModel
import com.sermilion.kmpcomposestarter.core.data.model.UserDataModel
import com.sermilion.kmpcomposestarter.core.data.util.withRestErrorHandling
import com.sermilion.kmpcomposestarter.core.domain.model.AuthError
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

/**
 * The starter's only bound [AuthRemoteDataSource]: an in-process fake that accepts the demo
 * credentials and rejects everything else.
 *
 * It is the default binding rather than a parallel stack, so a fork replaces it by swapping this
 * one `@ContributesBinding` for an implementation that talks to the injected `HttpClient`.
 */
@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class MockAuthRemoteDataSource : AuthRemoteDataSource {
  override suspend fun login(
    email: String,
    password: String,
  ): AuthResultDataModel =
    withRestErrorHandling(
      tag = TAG,
      block = {
        delay(MOCK_DELAY_MS)
        if (email == MockConfig.DEMO_EMAIL && password == MockConfig.DEMO_PASSWORD) {
          AuthResultDataModel.Success(
            user =
              UserDataModel(
                id = MockConfig.DEMO_USER_ID,
                email = email,
                name = MockConfig.DEMO_USER_NAME,
              ),
            token = issuedToken(),
          )
        } else {
          AuthResultDataModel.Failure(AuthError.InvalidCredentials)
        }
      },
      errorBlock = { AuthResultDataModel.Failure(AuthError.Unexpected(it)) },
    )

  override suspend fun register(
    email: String,
    password: String,
    name: String,
  ): AuthResultDataModel =
    withRestErrorHandling(
      tag = TAG,
      block = {
        delay(MOCK_DELAY_MS)
        AuthResultDataModel.Success(
          user =
            UserDataModel(
              id = "user-${Clock.System.now().toEpochMilliseconds()}",
              email = email,
              name = name,
            ),
          token = issuedToken(),
        )
      },
      errorBlock = { AuthResultDataModel.Failure(AuthError.Unexpected(it)) },
    )

  override suspend fun logout() {
    delay(MOCK_LOGOUT_DELAY_MS)
  }

  private fun issuedToken() =
    AuthTokenDataModel(
      accessToken = "mock-access-${Clock.System.now().toEpochMilliseconds()}",
    )

  private companion object {
    const val TAG = "MockAuthRemoteDataSource"
    const val MOCK_DELAY_MS = 1000L
    const val MOCK_LOGOUT_DELAY_MS = 500L
  }
}
