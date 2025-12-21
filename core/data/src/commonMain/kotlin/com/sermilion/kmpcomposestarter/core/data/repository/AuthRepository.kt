package com.sermilion.kmpcomposestarter.core.data.repository

import com.sermilion.kmpcomposestarter.common.di.SingleIn
import com.sermilion.kmpcomposestarter.core.data.api.AuthApiService
import com.sermilion.kmpcomposestarter.core.data.api.AuthResponse
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

interface AuthRepository {
  val isLoggedIn: StateFlow<Boolean>
  val currentUser: UserData?

  suspend fun login(email: String, password: String): LoginResult
  suspend fun register(email: String, password: String, name: String): LoginResult
  suspend fun logout()
}

sealed interface LoginResult {
  data class Success(val userData: UserData) : LoginResult
  data class Error(val message: String) : LoginResult
}

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class StarterAuthRepository(
  private val authApiService: AuthApiService,
  private val userComponentManager: UserComponentManager
) : AuthRepository {

  override val isLoggedIn: StateFlow<Boolean>
    get() = userComponentManager.userComponentFlow.map { it != null } as StateFlow<Boolean>

  override val currentUser: UserData?
    get() = userComponentManager.userComponent?.userData

  override suspend fun login(email: String, password: String): LoginResult {
    return when (val response = authApiService.login(email, password)) {
      is AuthResponse.Success -> {
        userComponentManager.createComponent(response.userData)
        LoginResult.Success(response.userData)
      }
      is AuthResponse.Error -> LoginResult.Error(response.message)
    }
  }

  override suspend fun register(email: String, password: String, name: String): LoginResult {
    return when (val response = authApiService.register(email, password, name)) {
      is AuthResponse.Success -> {
        userComponentManager.createComponent(response.userData)
        LoginResult.Success(response.userData)
      }
      is AuthResponse.Error -> LoginResult.Error(response.message)
    }
  }

  override suspend fun logout() {
    authApiService.logout()
    userComponentManager.destroyComponent()
  }
}
