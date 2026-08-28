package com.sermilion.kmpcomposestarter.core.data.remote

import com.sermilion.kmpcomposestarter.core.data.config.MockConfig
import com.sermilion.kmpcomposestarter.core.data.model.AuthResultDataModel
import com.sermilion.kmpcomposestarter.core.data.model.AuthTokenDataModel
import com.sermilion.kmpcomposestarter.core.data.model.UserDataModel
import com.sermilion.kmpcomposestarter.core.data.util.withRestErrorHandling
import com.sermilion.kmpcomposestarter.core.domain.model.AuthError
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class KtorAuthRemoteDataSource(@Suppress("unused") private val httpClient: HttpClient) :
  AuthRemoteDataSource {

  override suspend fun login(email: String, password: String): AuthResultDataModel =
    withRestErrorHandling(
      tag = TAG,
      block = {
        delay(MOCK_DELAY_MS)
        if (email == MockConfig.DEMO_EMAIL && password == MockConfig.DEMO_PASSWORD) {
          AuthResultDataModel.Success(
            user = UserDataModel(
              id = MockConfig.DEMO_USER_ID,
              email = email,
              name = MockConfig.DEMO_USER_NAME,
            ),
            token = mockToken("mock-token"),
          )
        } else {
          AuthResultDataModel.Failure(AuthError.InvalidCredentials)
        }
      },
      errorBlock = { AuthResultDataModel.Failure(AuthError.Network) },
    )

  override suspend fun register(
    email: String,
    password: String,
    name: String,
  ): AuthResultDataModel = withRestErrorHandling(
    tag = TAG,
    block = {
      delay(MOCK_DELAY_MS)
      AuthResultDataModel.Success(
        user = UserDataModel(
          id = "user-${Clock.System.now().toEpochMilliseconds()}",
          email = email,
          name = name,
        ),
        token = mockToken("mock-token"),
      )
    },
    errorBlock = { AuthResultDataModel.Failure(AuthError.Network) },
  )

  override suspend fun logout() {
    delay(MOCK_LOGOUT_DELAY_MS)
  }

  override suspend fun refreshToken(token: String): AuthResultDataModel = withRestErrorHandling(
    tag = TAG,
    block = {
      delay(MOCK_LOGOUT_DELAY_MS)
      AuthResultDataModel.Success(
        user = UserDataModel(
          id = MockConfig.DEMO_USER_ID,
          email = MockConfig.DEMO_EMAIL,
          name = MockConfig.DEMO_USER_NAME,
        ),
        token = mockToken("refreshed-token"),
      )
    },
    errorBlock = { AuthResultDataModel.Failure(AuthError.RefreshFailed) },
  )

  override suspend fun getCurrentUser(token: String): UserDataModel? = null

  private fun mockToken(prefix: String) =
    AuthTokenDataModel(accessToken = "$prefix-${Clock.System.now().toEpochMilliseconds()}")

  private companion object {
    const val TAG = "KtorAuthRemoteDataSource"
    const val MOCK_DELAY_MS = 1000L
    const val MOCK_LOGOUT_DELAY_MS = 500L
  }
}
