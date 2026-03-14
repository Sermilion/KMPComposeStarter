package com.sermilion.kmpcomposestarter.core.data.db.dao

import com.sermilion.kmpcomposestarter.common.di.UserScope
import com.sermilion.kmpcomposestarter.core.data.db.UserDataModel
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import com.sermilion.kmpcomposestarter.core.data.model.UserLocalDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Inject
@SingleIn(UserScope::class)
class StarterUserDao(private val database: UserDatabase) : UserDao {

  private val userDao = database.userEntityDao()

  override fun observeUsers(): Flow<List<UserLocalDataModel>> = userDao.observeUsers()
    .map { list -> list.map(UserDataModel::toLocalDataModel) }

  override fun observeUser(userId: Uuid): Flow<UserLocalDataModel?> =
    userDao.observeById(userId.toString()).map { it?.toLocalDataModel() }

  override suspend fun findUser(userId: Uuid): UserLocalDataModel? =
    userDao.getById(userId.toString())?.toLocalDataModel()

  override suspend fun insertUser(user: UserLocalDataModel) {
    userDao.upsert(user.toDataModel())
  }

  override suspend fun insertUsers(users: List<UserLocalDataModel>) {
    userDao.upsertAll(users.map(UserLocalDataModel::toDataModel))
  }

  override suspend fun deleteUser(userId: Uuid) {
    userDao.deleteById(userId.toString())
  }

  override suspend fun deleteAllUsers() {
    userDao.deleteAll()
  }
}

@OptIn(ExperimentalUuidApi::class)
private fun UserDataModel.toLocalDataModel() = UserLocalDataModel(
  id = Uuid.parse(id),
  name = name,
  email = email,
  createdAt = Instant.fromEpochMilliseconds(createdAt),
)

@OptIn(ExperimentalUuidApi::class)
private fun UserLocalDataModel.toDataModel() = UserDataModel(
  id = id.toString(),
  name = name,
  email = email,
  createdAt = createdAt.toEpochMilliseconds(),
)
