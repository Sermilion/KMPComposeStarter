package com.sermilion.kmpcomposestarter.core.data.network

import com.sermilion.kmpcomposestarter.core.domain.auth.AuthToken
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import co.touchlab.kermit.Logger as KermitLogger

/**
 * Unknown fields are tolerated on purpose: a backend adding a field must not break a shipped
 * client that has not been updated yet.
 */
internal val StarterJson: Json = Json { ignoreUnknownKeys = true }

/**
 * Installs the plugins every request in this app needs.
 *
 * Kept separate from the DI module so a test can drive the exact production configuration over
 * `MockEngine` — the sanitized-logging behaviour below is only worth anything if the thing under
 * test is the same configuration that ships.
 */
internal fun HttpClientConfig<*>.configureStarterHttpClient(
  loadToken: suspend () -> AuthToken?,
  ktorLogger: Logger,
) {
  install(ContentNegotiation) { json(StarterJson) }

  install(Auth) {
    bearer {
      loadTokens {
        loadToken()?.let { token ->
          BearerTokens(accessToken = token.accessToken, refreshToken = token.refreshToken)
        }
      }
      // The bundled mock issues no refresh token, so there is nothing honest to exchange here:
      // returning null lets the request fail as unauthorized instead of inventing a credential.
      // A fork with a real backend posts the refresh token and returns the pair it gets back.
      refreshTokens { null }
      // Every endpoint behind this client is authenticated, so attach the token up front rather
      // than paying an extra round trip to be told so.
      sendWithoutRequest { true }
    }
  }

  install(Logging) {
    logger = ktorLogger
    level = LogLevel.HEADERS
    // Without this the bearer token is printed verbatim on every request.
    sanitizeHeader { header -> header == HttpHeaders.Authorization }
  }

  defaultRequest { url(NetworkConfig.BASE_URL) }
}

/** Routes Ktor's logging through the app's logger, which release builds already filter. */
internal object KermitKtorLogger : Logger {
  override fun log(message: String) {
    KermitLogger.d(TAG) { message }
  }

  private const val TAG = "HttpClient"
}
