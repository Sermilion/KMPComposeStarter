
package news.readian.notoesapp.core.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Inject
import news.readian.notoesapp.core.data.api.AuthApiService
import news.readian.notoesapp.core.data.api.AuthResponse
import news.readian.notoesapp.core.domain.di.UserComponentManager
import news.readian.notoesapp.core.domain.model.UserData
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

interface AuthRepository {
  val isLoggedIn: StateFlow<Boolean>
  val currentUser: UserData?

  suspend fun login(email: String, password: String): LoginResult
  suspend fun register(email: String, password: String, name: String): LoginResult
  suspend fun loginGuest(): LoginResult
  suspend fun logout()
}

sealed interface LoginResult {
  data class Success(val userData: UserData) : LoginResult
  data class Error(val message: String, val code: String? = null, val statusCode: Int? = null) :
    LoginResult
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
      is AuthResponse.Error -> LoginResult.Error(
        message = response.message,
        code = response.code,
        statusCode = response.statusCode,
      )
    }

  override suspend fun register(email: String, password: String, name: String): LoginResult =
    when (val response = authApiService.register(email, password, name)) {
      is AuthResponse.Success -> {
        userComponentManager.createComponent(response.userData)
        LoginResult.Success(response.userData)
      }
      is AuthResponse.Error -> LoginResult.Error(
        message = response.message,
        code = response.code,
        statusCode = response.statusCode,
      )
    }

  override suspend fun loginGuest(): LoginResult =
    when (val response = authApiService.loginGuest()) {
      is AuthResponse.Success -> {
        userComponentManager.createComponent(response.userData)
        LoginResult.Success(response.userData)
      }
      is AuthResponse.Error -> LoginResult.Error(
        message = response.message,
        code = response.code,
        statusCode = response.statusCode,
      )
    }

  override suspend fun logout() {
    currentUser?.token?.takeIf { it.isNotBlank() }?.let { token ->
      authApiService.logout(token)
    }
    userComponentManager.destroyComponent()
  }
}
