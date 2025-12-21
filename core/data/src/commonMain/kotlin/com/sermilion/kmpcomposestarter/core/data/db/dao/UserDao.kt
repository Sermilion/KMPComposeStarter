package com.sermilion.kmpcomposestarter.core.data.db.dao

import com.sermilion.kmpcomposestarter.core.data.model.UserLocalDataModel
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface UserDao {
  fun observeUsers(): Flow<List<UserLocalDataModel>>
  fun observeUser(userId: Uuid): Flow<UserLocalDataModel?>
  suspend fun findUser(userId: Uuid): UserLocalDataModel?
  suspend fun insertUser(user: UserLocalDataModel)
  suspend fun insertUsers(users: List<UserLocalDataModel>)
  suspend fun deleteUser(userId: Uuid)
  suspend fun deleteAllUsers()
}
