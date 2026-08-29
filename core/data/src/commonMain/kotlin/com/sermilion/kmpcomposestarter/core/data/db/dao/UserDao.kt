package com.sermilion.kmpcomposestarter.core.data.db.dao

import com.sermilion.kmpcomposestarter.core.data.model.UserLocalDataModel
import kotlinx.coroutines.flow.Flow

interface UserDao {
  fun observeUsers(): Flow<List<UserLocalDataModel>>

  fun observeUser(userId: String): Flow<UserLocalDataModel?>

  suspend fun findUser(userId: String): UserLocalDataModel?

  suspend fun insertUser(user: UserLocalDataModel)

  suspend fun insertUsers(users: List<UserLocalDataModel>)

  suspend fun deleteUser(userId: String)

  suspend fun deleteAllUsers()
}
