package com.sermilion.kmpcomposestarter.core.data.local

import com.sermilion.kmpcomposestarter.core.data.model.AuthTokenDataModel
import com.sermilion.kmpcomposestarter.core.data.model.UserDataModel
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
  fun observeCurrentUser(): Flow<UserDataModel?>
  suspend fun getCurrentUser(): UserDataModel?
  suspend fun getAuthToken(): AuthTokenDataModel?
  suspend fun saveUser(user: UserDataModel)
  suspend fun saveAuthToken(token: AuthTokenDataModel)
  suspend fun clearUserData()
}
