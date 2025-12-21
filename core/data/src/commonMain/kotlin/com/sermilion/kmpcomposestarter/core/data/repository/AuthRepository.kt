package com.sermilion.kmpcomposestarter.core.data.repository

import com.sermilion.kmpcomposestarter.core.data.api.AuthApiService
import com.sermilion.kmpcomposestarter.core.data.api.AuthResponse
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

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
  private val userComponentManager: UserComponentManager,
  externalScope: CoroutineScope,
) : AuthRepository {

  override val isLoggedIn: StateFlow<Boolean> = userComponentManager.userComponentFlow
    .map { it != null }
    .stateIn(
      scope = externalScope,
      started = SharingStarted.Eagerly,
      initialValue = userComponentManager.userComponent != null,
    )

  override val currentUser: UserData?
    get() = userComponentManager.userComponent?.userData

  override suspend fun login(email: String, password: String): LoginResult =
    when (val response = authApiService.login(email, password)) {
      is AuthResponse.Success -> {
        userComponentManager.createComponent(response.userData)
        LoginResult.Success(response.userData)
      }
      is AuthResponse.Error -> LoginResult.Error(response.message)
    }

  override suspend fun register(email: String, password: String, name: String): LoginResult =
    when (val response = authApiService.register(email, password, name)) {
      is AuthResponse.Success -> {
        userComponentManager.createComponent(response.userData)
        LoginResult.Success(response.userData)
      }
      is AuthResponse.Error -> LoginResult.Error(response.message)
    }

  override suspend fun logout() {
    authApiService.logout()
    userComponentManager.destroyComponent()
  }
}
