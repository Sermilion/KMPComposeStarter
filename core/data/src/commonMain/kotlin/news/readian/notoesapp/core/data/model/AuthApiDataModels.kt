
package news.readian.notoesapp.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginPayloadDataModel(val identifier: String, val password: String)

@Serializable
data class RegisterPayloadDataModel(
  val username: String,
  val password: String,
  val email: String,
  @SerialName("password_confirmation")
  val confirmPassword: String,
)

@Serializable
data class GuestRegistrationPayloadDataModel(
  val password: String,
  @SerialName("password_confirmation")
  val confirmPassword: String,
)

@Serializable
data class LoginResponseDataModel(val success: Boolean, val data: LoginDataDataModel)

@Serializable
data class LoginDataDataModel(val user: AuthUserDataModel, val token: AuthTokenResponseDataModel)

@Serializable
data class AuthTokenResponseDataModel(
  @SerialName("access_token")
  val accessToken: String,
  @SerialName("refresh_token")
  val refreshToken: String,
  @SerialName("expires_in")
  val expiresIn: Long? = null,
)

@Serializable
data class RegistrationResponseDataModel(val success: Boolean, val data: AuthUserDataModel)

@Serializable
data class GuestRegistrationResponseDataModel(val success: Boolean, val data: AuthUserDataModel)

@Serializable
data class AuthUserDataModel(
  val id: String,
  val username: String,
  val name: String? = null,
  val email: String? = null,
)

@Serializable
data class ApiErrorResponseDataModel(val code: String? = null, val message: String? = null)
