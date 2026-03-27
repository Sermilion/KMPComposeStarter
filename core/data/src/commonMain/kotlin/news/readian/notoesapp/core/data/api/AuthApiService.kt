
package news.readian.notoesapp.core.data.api

import news.readian.notoesapp.core.domain.model.UserData

interface AuthApiService {
  suspend fun login(email: String, password: String): AuthResponse
  suspend fun register(email: String, password: String, name: String): AuthResponse
  suspend fun loginGuest(): AuthResponse
  suspend fun logout(token: String)
}

sealed interface AuthResponse {
  data class Success(val userData: UserData) : AuthResponse
  data class Error(val message: String, val code: String? = null, val statusCode: Int? = null) :
    AuthResponse
}
