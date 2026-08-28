package com.sermilion.kmpcomposestarter.core.data.model

import com.sermilion.kmpcomposestarter.core.domain.model.AuthError
import kotlinx.serialization.Serializable

@Serializable
data class UserDataModel(val id: String, val email: String, val name: String)

@Serializable
data class AuthTokenDataModel(
  val accessToken: String,
  val refreshToken: String? = null,
  val expiresIn: Long? = null,
)

sealed interface AuthResultDataModel {
  data class Success(val user: UserDataModel, val token: AuthTokenDataModel) : AuthResultDataModel

  data class Failure(val error: AuthError) : AuthResultDataModel
}
