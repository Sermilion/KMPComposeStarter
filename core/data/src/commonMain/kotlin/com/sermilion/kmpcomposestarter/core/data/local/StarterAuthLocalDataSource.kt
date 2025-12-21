package com.sermilion.kmpcomposestarter.core.data.local

import com.sermilion.kmpcomposestarter.core.data.model.AuthTokenDataModel
import com.sermilion.kmpcomposestarter.core.data.model.UserDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class StarterAuthLocalDataSource : AuthLocalDataSource {

  private val _currentUser = MutableStateFlow<UserDataModel?>(null)
  private var _authToken: AuthTokenDataModel? = null

  override fun observeCurrentUser(): Flow<UserDataModel?> = _currentUser.asStateFlow()

  override suspend fun getCurrentUser(): UserDataModel? = _currentUser.value

  override suspend fun getAuthToken(): AuthTokenDataModel? = _authToken

  override suspend fun saveUser(user: UserDataModel) {
    _currentUser.value = user
  }

  override suspend fun saveAuthToken(token: AuthTokenDataModel) {
    _authToken = token
  }

  override suspend fun clearUserData() {
    _currentUser.value = null
    _authToken = null
  }
}
