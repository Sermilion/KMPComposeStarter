
package news.readian.notoesapp.core.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import me.tatarka.inject.annotations.Inject
import news.readian.notoesapp.core.data.model.ApiErrorResponseDataModel
import news.readian.notoesapp.core.data.model.AuthResultDataModel
import news.readian.notoesapp.core.data.model.AuthTokenDataModel
import news.readian.notoesapp.core.data.model.AuthUserDataModel
import news.readian.notoesapp.core.data.model.GuestRegistrationPayloadDataModel
import news.readian.notoesapp.core.data.model.GuestRegistrationResponseDataModel
import news.readian.notoesapp.core.data.model.LoginPayloadDataModel
import news.readian.notoesapp.core.data.model.LoginResponseDataModel
import news.readian.notoesapp.core.data.model.RegisterPayloadDataModel
import news.readian.notoesapp.core.data.model.RegistrationResponseDataModel
import news.readian.notoesapp.core.data.model.UserDataModel
import news.readian.notoesapp.core.data.util.withRestErrorHandling
import news.readian.notoesapp.core.domain.common.randomUuid
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class KtorAuthRemoteDataSource(private val httpClient: HttpClient, private val json: Json) :
  AuthRemoteDataSource {

  override suspend fun login(email: String, password: String): AuthResultDataModel =
    withRestErrorHandling(
      tag = TAG,
      block = {
        val response = httpClient.post("api/auth/token") {
          contentType(ContentType.Application.Json)
          setBody(LoginPayloadDataModel(identifier = email, password = password))
        }.body<LoginResponseDataModel>()

        AuthResultDataModel.Success(
          user = response.data.user.toUserDataModel(defaultEmail = email),
          token = AuthTokenDataModel(
            accessToken = response.data.token.accessToken,
            refreshToken = response.data.token.refreshToken,
            expiresIn = response.data.token.expiresIn,
          ),
        )
      },
      errorBlock = { exception ->
        exception.toAuthError(defaultMessage = "Unable to sign in")
      },
    )

  override suspend fun register(
    email: String,
    password: String,
    name: String,
  ): AuthResultDataModel = withRestErrorHandling(
    tag = TAG,
    block = {
      val response = httpClient.post("api/users") {
        contentType(ContentType.Application.Json)
        setBody(
          RegisterPayloadDataModel(
            username = name,
            password = password,
            email = email,
            confirmPassword = password,
          ),
        )
      }.body<RegistrationResponseDataModel>()

      AuthResultDataModel.Registered(
        user = response.data.toUserDataModel(defaultEmail = email),
      )
    },
    errorBlock = { exception ->
      exception.toAuthError(defaultMessage = "Unable to create account")
    },
  )

  override suspend fun loginGuest(): AuthResultDataModel = withRestErrorHandling(
    tag = TAG,
    block = {
      val password = randomUuid().toString()
      val response = httpClient.post("api/users/guest") {
        contentType(ContentType.Application.Json)
        setBody(
          GuestRegistrationPayloadDataModel(
            password = password,
            confirmPassword = password,
          ),
        )
      }.body<GuestRegistrationResponseDataModel>()

      login(response.data.username, password)
    },
    errorBlock = { exception ->
      exception.toAuthError(defaultMessage = "Unable to continue as guest")
    },
  )

  override suspend fun logout(token: String) {
    withRestErrorHandling(
      tag = TAG,
      block = {
        httpClient.post("api/auth/logout") {
          bearerAuth(token)
        }.body<Unit>()
      },
      errorBlock = { Unit },
    )
  }

  override suspend fun refreshToken(token: String): AuthResultDataModel =
    throw NotImplementedError("Refresh token endpoint is not implemented")

  override suspend fun getCurrentUser(token: String): UserDataModel? = null

  private suspend fun Exception.toAuthError(defaultMessage: String): AuthResultDataModel.Error =
    when (this) {
      is HttpRequestTimeoutException -> AuthResultDataModel.Error(
        message = defaultMessage,
        code = "TIMEOUT",
      )
      is ClientRequestException -> {
        val bodyText = readResponseBody()
        val payload = parseErrorPayload(bodyText)
        AuthResultDataModel.Error(
          message = payload.message ?: response.status.toDefaultMessage(defaultMessage),
          code = payload.code,
          statusCode = response.status.value,
        )
      }
      is ServerResponseException -> {
        val bodyText = readResponseBody()
        val payload = parseErrorPayload(bodyText)
        AuthResultDataModel.Error(
          message = payload.message ?: defaultMessage,
          code = payload.code,
          statusCode = response.status.value,
        )
      }
      else -> AuthResultDataModel.Error(message = message.orEmpty().ifBlank { defaultMessage })
    }

  private suspend fun ClientRequestException.readResponseBody(): String = try {
    response.bodyAsText()
  } catch (_: Exception) {
    ""
  }

  private suspend fun ServerResponseException.readResponseBody(): String = try {
    response.bodyAsText()
  } catch (_: Exception) {
    ""
  }

  private fun parseErrorPayload(bodyText: String): ApiErrorResponseDataModel = try {
    json.decodeFromString<ApiErrorResponseDataModel>(bodyText)
  } catch (_: SerializationException) {
    try {
      val jsonElement = json.parseToJsonElement(bodyText).jsonObject
      ApiErrorResponseDataModel(
        code = jsonElement["code"]?.jsonPrimitive?.content,
        message = jsonElement["message"]?.jsonPrimitive?.content,
      )
    } catch (_: Exception) {
      ApiErrorResponseDataModel()
    }
  } catch (_: Exception) {
    ApiErrorResponseDataModel()
  }

  private fun HttpStatusCode.toDefaultMessage(defaultMessage: String): String = when (this) {
    HttpStatusCode.Unauthorized -> "Invalid credentials"
    HttpStatusCode.Conflict -> "An account with these details already exists"
    HttpStatusCode.UnprocessableEntity -> "Please check your account details and try again"
    else -> defaultMessage
  }

  private fun AuthUserDataModel.toUserDataModel(defaultEmail: String): UserDataModel =
    UserDataModel(
      id = id,
      email = email.orEmpty().ifBlank { defaultEmail },
      name = name.orEmpty().ifBlank { username },
    )

  private companion object {
    const val TAG = "KtorAuthRemoteDataSource"
  }
}
