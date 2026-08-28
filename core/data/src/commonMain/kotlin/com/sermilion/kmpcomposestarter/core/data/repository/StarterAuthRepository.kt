package com.sermilion.kmpcomposestarter.core.data.repository

import com.sermilion.kmpcomposestarter.core.data.api.AuthApiService
import com.sermilion.kmpcomposestarter.core.data.api.AuthResponse
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.model.LoginResult
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.core.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

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
    startSession(authApiService.login(email, password))

  override suspend fun register(email: String, password: String, name: String): LoginResult =
    startSession(authApiService.register(email, password, name))

  /**
   * Signs out remotely, but tears the local session down whether or not that call succeeds: a
   * failed or cancelled network sign-out must never leave an authenticated session alive. The
   * network failure still surfaces, after teardown.
   */
  override suspend fun logout() {
    try {
      authApiService.logout()
    } finally {
      userComponentManager.destroyComponent()
    }
  }

  private suspend fun startSession(response: AuthResponse): LoginResult = when (response) {
    is AuthResponse.Success -> {
      val session = userComponentManager.createComponent(response.userData)
      session.tokenStore.save(response.token)
      LoginResult.Success(response.userData)
    }
    is AuthResponse.Failure -> LoginResult.Failure(response.error)
  }
}
