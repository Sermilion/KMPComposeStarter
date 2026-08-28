package com.sermilion.kmpcomposestarter.core.domain.repository

import com.sermilion.kmpcomposestarter.core.domain.model.LoginResult
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
  val isLoggedIn: StateFlow<Boolean>
  val currentUser: UserData?

  suspend fun login(email: String, password: String): LoginResult
  suspend fun register(email: String, password: String, name: String): LoginResult
  suspend fun logout()
}
