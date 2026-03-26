
package news.readian.notoesapp.core.data.api

import me.tatarka.inject.annotations.Inject
import news.readian.notoesapp.core.data.model.AuthResultDataModel
import news.readian.notoesapp.core.data.remote.AuthRemoteDataSource
import news.readian.notoesapp.core.domain.model.UserData
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.random.Random

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class ReadianAuthApiService(private val authRemoteDataSource: AuthRemoteDataSource) :
  AuthApiService {

  override suspend fun login(email: String, password: String): AuthResponse =
    when (val response = authRemoteDataSource.login(email, password)) {
      is AuthResultDataModel.Success -> AuthResponse.Success(
        userData = UserData(
          id = response.user.id,
          email = response.user.email,
          name = response.user.name,
          token = response.token.accessToken,
        ),
      )
      is AuthResultDataModel.Registered -> AuthResponse.Error(
        message = "Unexpected registration response during login",
      )
      is AuthResultDataModel.GuestRegistered -> AuthResponse.Error(
        message = "Unexpected guest registration response during login",
      )
      is AuthResultDataModel.Error -> AuthResponse.Error(
        message = response.message,
        code = response.code,
        statusCode = response.statusCode,
      )
    }

  override suspend fun register(email: String, password: String, name: String): AuthResponse =
    when (val response = authRemoteDataSource.register(email, password, name)) {
      is AuthResultDataModel.Success, is AuthResultDataModel.Registered -> login(email, password)
      is AuthResultDataModel.GuestRegistered -> AuthResponse.Error(
        message = "Unexpected guest registration response during registration",
      )
      is AuthResultDataModel.Error -> AuthResponse.Error(
        message = response.message,
        code = response.code,
        statusCode = response.statusCode,
      )
    }

  override suspend fun loginGuest(): AuthResponse {
    val password = generateGuestPassword()
    return when (val response = authRemoteDataSource.registerGuest(password)) {
      is AuthResultDataModel.GuestRegistered -> login(response.username, response.password)
      is AuthResultDataModel.Error -> AuthResponse.Error(
        message = response.message,
        code = response.code,
        statusCode = response.statusCode,
      )
      is AuthResultDataModel.Success -> AuthResponse.Error(
        message = "Unexpected login response during guest registration",
      )
      is AuthResultDataModel.Registered -> AuthResponse.Error(
        message = "Unexpected registration response during guest registration",
      )
    }
  }

  override suspend fun logout() {
    authRemoteDataSource.logout()
  }

  private fun generateGuestPassword(length: Int = GUEST_PASSWORD_LENGTH): String = buildString {
    repeat(length) {
      append(GUEST_PASSWORD_CHARSET[Random.nextInt(GUEST_PASSWORD_CHARSET.length)])
    }
  }

  private companion object {
    const val GUEST_PASSWORD_LENGTH = 32
    const val GUEST_PASSWORD_CHARSET =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
  }
}
