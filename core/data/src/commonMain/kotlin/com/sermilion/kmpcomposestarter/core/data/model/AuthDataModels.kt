package com.sermilion.kmpcomposestarter.core.data.model

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

  data class Error(val message: String, val code: String? = null) : AuthResultDataModel
}
