package com.sermilion.kmpcomposestarter.core.domain.model

sealed interface LoginResult {
  data class Success(val userData: UserData) : LoginResult

  data class Failure(val error: AuthError) : LoginResult
}
