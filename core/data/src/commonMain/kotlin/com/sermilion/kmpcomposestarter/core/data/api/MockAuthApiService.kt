package com.sermilion.kmpcomposestarter.core.data.api

import com.sermilion.kmpcomposestarter.common.di.SingleIn
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.delay
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class MockAuthApiService : AuthApiService {

  override suspend fun login(email: String, password: String): AuthResponse {
    delay(1000)
    return if (email == "test@test.com" && password == "password") {
      AuthResponse.Success(
        userData = UserData(
          id = "user-123",
          email = email,
          name = "Test User",
          token = "mock-token-${System.currentTimeMillis()}"
        )
      )
    } else {
      AuthResponse.Error("Invalid credentials")
    }
  }

  override suspend fun register(email: String, password: String, name: String): AuthResponse {
    delay(1000)
    return AuthResponse.Success(
      userData = UserData(
        id = "user-${System.currentTimeMillis()}",
        email = email,
        name = name,
        token = "mock-token-${System.currentTimeMillis()}"
      )
    )
  }

  override suspend fun logout() {
    delay(500)
  }
}
