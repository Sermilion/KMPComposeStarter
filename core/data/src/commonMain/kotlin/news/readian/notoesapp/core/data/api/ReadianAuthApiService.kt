
package news.readian.notoesapp.core.data.api

import me.tatarka.inject.annotations.Inject
import news.readian.notoesapp.core.data.model.AuthResultDataModel
import news.readian.notoesapp.core.data.remote.AuthRemoteDataSource
import news.readian.notoesapp.core.domain.model.UserData
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class ReadianAuthApiService(private val authRemoteDataSource: AuthRemoteDataSource) :
  AuthApiService {

  override suspend fun login(email: String, password: String): AuthResponse =
    when (val response = authRemoteDataSource.login(email, password)) {
      is AuthResultDataModel.Success -> response.toAuthSuccess()
      is AuthResultDataModel.Registered -> AuthResponse.Error(
        message = "Unexpected registration response during login",
      )
      is AuthResultDataModel.Error -> response.toAuthError()
    }

  override suspend fun register(email: String, password: String, name: String): AuthResponse {
    val registrationResponse = authRemoteDataSource.register(email, password, name)
    return when (registrationResponse) {
      is AuthResultDataModel.Success -> registrationResponse.toAuthSuccess()
      is AuthResultDataModel.Registered -> {
        when (val loginResponse = authRemoteDataSource.login(email, password)) {
          is AuthResultDataModel.Success -> loginResponse.toAuthSuccess()
          is AuthResultDataModel.Registered -> AuthResponse.Error(
            message = "Unexpected registration response during automatic login",
          )
          is AuthResultDataModel.Error -> AuthResponse.Error(
            message = "Account created. Please log in.",
            code = REGISTERED_LOGIN_FAILED_CODE,
            statusCode = loginResponse.statusCode,
          )
        }
      }
      is AuthResultDataModel.Error -> registrationResponse.toAuthError()
    }
  }

  override suspend fun loginGuest(): AuthResponse =
    when (val response = authRemoteDataSource.loginGuest()) {
      is AuthResultDataModel.Success -> response.toAuthSuccess()
      is AuthResultDataModel.Registered -> AuthResponse.Error(
        message = "Unexpected registration response during guest login",
      )
      is AuthResultDataModel.Error -> response.toAuthError()
    }

  override suspend fun logout(token: String) {
    authRemoteDataSource.logout(token)
  }

  private fun AuthResultDataModel.Success.toAuthSuccess(): AuthResponse.Success =
    AuthResponse.Success(
      userData = UserData(
        id = user.id,
        email = user.email,
        name = user.name,
        token = token.accessToken,
      ),
    )

  private fun AuthResultDataModel.Error.toAuthError(): AuthResponse.Error = AuthResponse.Error(
    message = message,
    code = code,
    statusCode = statusCode,
  )

  private companion object {
    const val REGISTERED_LOGIN_FAILED_CODE = "REGISTERED_LOGIN_FAILED"
  }
}
