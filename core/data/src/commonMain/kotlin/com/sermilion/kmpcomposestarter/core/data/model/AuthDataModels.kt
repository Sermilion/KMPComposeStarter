package com.sermilion.kmpcomposestarter.core.data.model

import com.sermilion.kmpcomposestarter.core.domain.model.AuthError
import kotlinx.serialization.Serializable

@Serializable
data class UserDataModel(val id: String, val email: String, val name: String)

@Serializable
data class AuthTokenDataModel(
  val accessToken: String,
  val refreshToken: String? = null,
  val expiresAtEpochMillis: Long? = null,
)

sealed interface AuthResultDataModel {
  data class Success(val user: UserDataModel, val token: AuthTokenDataModel) : AuthResultDataModel

  data class Failure(val error: AuthError) : AuthResultDataModel
}

/** A signed-in session as it is persisted: who is signed in, and what authorizes their requests. */
data class StoredSession(val user: UserDataModel, val token: AuthTokenDataModel)
