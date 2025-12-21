package com.sermilion.kmpcomposestarter.core.data.api

import com.sermilion.kmpcomposestarter.core.data.config.MockConfig
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class MockAuthApiService : AuthApiService {

  override suspend fun login(email: String, password: String): AuthResponse {
    delay(MOCK_DELAY_MS)
    return if (email == MockConfig.DEMO_EMAIL && password == MockConfig.DEMO_PASSWORD) {
      AuthResponse.Success(
        userData = UserData(
          id = MockConfig.DEMO_USER_ID,
          email = email,
          name = MockConfig.DEMO_USER_NAME,
          token = "mock-token-${Clock.System.now().toEpochMilliseconds()}",
        ),
      )
    } else {
      AuthResponse.Error("Invalid credentials")
    }
  }

  override suspend fun register(email: String, password: String, name: String): AuthResponse {
    delay(MOCK_DELAY_MS)
    return AuthResponse.Success(
      userData = UserData(
        id = "user-${Clock.System.now().toEpochMilliseconds()}",
        email = email,
        name = name,
        token = "mock-token-${Clock.System.now().toEpochMilliseconds()}",
      ),
    )
  }

  override suspend fun logout() {
    delay(MOCK_LOGOUT_DELAY_MS)
  }

  private companion object {
    const val MOCK_DELAY_MS = 1000L
    const val MOCK_LOGOUT_DELAY_MS = 500L
  }
}
