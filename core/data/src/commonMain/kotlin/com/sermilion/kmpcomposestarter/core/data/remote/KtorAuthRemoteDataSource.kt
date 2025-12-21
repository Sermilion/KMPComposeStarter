package com.sermilion.kmpcomposestarter.core.data.remote

import com.sermilion.kmpcomposestarter.core.data.config.MockConfig
import com.sermilion.kmpcomposestarter.core.data.model.AuthResultDataModel
import com.sermilion.kmpcomposestarter.core.data.model.AuthTokenDataModel
import com.sermilion.kmpcomposestarter.core.data.model.UserDataModel
import com.sermilion.kmpcomposestarter.core.data.util.withRestErrorHandling
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
            token = AuthTokenDataModel(
              accessToken = "mock-token-${Clock.System.now().toEpochMilliseconds()}",
            ),
          )
        } else {
          AuthResultDataModel.Error(
            message = "Invalid credentials",
            code = "INVALID_CREDENTIALS",
          )
        }
      },
      errorBlock = { e ->
        AuthResultDataModel.Error(
          message = e.message.orEmpty(),
          code = "NETWORK_ERROR",
        )
      },
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
        token = AuthTokenDataModel(
          accessToken = "mock-token-${Clock.System.now().toEpochMilliseconds()}",
        ),
      )
    },
    errorBlock = { e ->
      AuthResultDataModel.Error(
        message = e.message.orEmpty(),
        code = "NETWORK_ERROR",
      )
    },
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
        token = AuthTokenDataModel(
          accessToken = "refreshed-token-${Clock.System.now().toEpochMilliseconds()}",
        ),
      )
    },
    errorBlock = { e ->
      AuthResultDataModel.Error(
        message = e.message.orEmpty(),
        code = "REFRESH_FAILED",
      )
    },
  )

  override suspend fun getCurrentUser(token: String): UserDataModel? = null

  private companion object {
    const val TAG = "KtorAuthRemoteDataSource"
    const val MOCK_DELAY_MS = 1000L
    const val MOCK_LOGOUT_DELAY_MS = 500L
  }
}
