package com.sermilion.kmpcomposestarter.core.data.api

import com.sermilion.kmpcomposestarter.core.domain.auth.AuthToken
import com.sermilion.kmpcomposestarter.core.domain.model.AuthError
import com.sermilion.kmpcomposestarter.core.domain.model.UserData

interface AuthApiService {
  suspend fun login(email: String, password: String): AuthResponse
  suspend fun register(email: String, password: String, name: String): AuthResponse
  suspend fun logout()
}

sealed interface AuthResponse {
  data class Success(val userData: UserData, val token: AuthToken) : AuthResponse

  data class Failure(val error: AuthError) : AuthResponse
}
