package com.sermilion.kmpcomposestarter.core.domain.auth

data class AuthToken(
  val accessToken: String,
  val refreshToken: String? = null,
  val expiresAtEpochMillis: Long? = null,
)

/**
 * Session-scoped holder for the credentials that authorize outgoing requests. Lives and dies with
 * the user component, so a session swap can never leak the previous user's bearer token.
 */
interface TokenStore {
  suspend fun get(): AuthToken?

  suspend fun save(token: AuthToken)

  suspend fun clear()
}
