package com.sermilion.kmpcomposestarter.core.data.remote

import com.sermilion.kmpcomposestarter.core.data.model.AuthResultDataModel
import com.sermilion.kmpcomposestarter.core.data.model.UserDataModel

interface AuthRemoteDataSource {
  suspend fun login(email: String, password: String): AuthResultDataModel
  suspend fun register(email: String, password: String, name: String): AuthResultDataModel
  suspend fun logout()
  suspend fun refreshToken(token: String): AuthResultDataModel
  suspend fun getCurrentUser(token: String): UserDataModel?
}
