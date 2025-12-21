package com.sermilion.kmpcomposestarter.core.data.db.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.sermilion.kmpcomposestarter.common.coroutines.DispatcherProvider
import com.sermilion.kmpcomposestarter.common.di.UserScope
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
class StarterUserDao(
  private val database: UserDatabase,
  private val dispatcherProvider: DispatcherProvider,
) : UserDao {

  private val userQueries = database.userQueries

  override fun observeUsers(): Flow<List<UserLocalDataModel>> = userQueries.observeAll()
    .asFlow()
    .mapToList(dispatcherProvider.io)
    .map { list -> list.map { it.toLocalDataModel() } }

  override fun observeUser(userId: Uuid): Flow<UserLocalDataModel?> =
    userQueries.observeById(userId.toString())
      .asFlow()
      .mapToOneOrNull(dispatcherProvider.io)
      .map { it?.toLocalDataModel() }

  override suspend fun findUser(userId: Uuid): UserLocalDataModel? =
    userQueries.selectById(userId.toString()).executeAsOneOrNull()?.toLocalDataModel()

  override suspend fun insertUser(user: UserLocalDataModel) {
    userQueries.insert(
      id = user.id.toString(),
      name = user.name,
      email = user.email,
      createdAt = user.createdAt.toEpochMilliseconds(),
    )
  }

  override suspend fun insertUsers(users: List<UserLocalDataModel>) {
    database.transaction {
      users.forEach { user ->
        userQueries.insert(
          id = user.id.toString(),
          name = user.name,
          email = user.email,
          createdAt = user.createdAt.toEpochMilliseconds(),
        )
      }
    }
  }

  override suspend fun deleteUser(userId: Uuid) {
    userQueries.deleteById(userId.toString())
  }

  override suspend fun deleteAllUsers() {
    userQueries.deleteAll()
  }

  private fun com.sermilion.kmpcomposestarter.core.data.db.UserEntity.toLocalDataModel() =
    UserLocalDataModel(
      id = Uuid.parse(id),
      name = name,
      email = email,
      createdAt = Instant.fromEpochMilliseconds(createdAt),
    )
}
