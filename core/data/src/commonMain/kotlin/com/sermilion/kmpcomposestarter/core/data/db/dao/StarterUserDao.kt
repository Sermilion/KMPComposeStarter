package com.sermilion.kmpcomposestarter.core.data.db.dao

import com.sermilion.kmpcomposestarter.common.di.UserScope
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import com.sermilion.kmpcomposestarter.core.data.db.UserEntity
import com.sermilion.kmpcomposestarter.core.data.model.UserLocalDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.time.Instant

/**
 * Shared wrapper over the Room-generated DAO. Entity-to-local mapping and time conversion live
 * here so calling code never touches [UserEntity].
 */
@Inject
@SingleIn(UserScope::class)
class StarterUserDao(
  private val database: UserDatabase,
) : UserDao {
  private val userDao = database.userEntityDao()

  override fun observeUser(userId: String): Flow<UserLocalDataModel?> =
    userDao.observeById(userId).map { it?.toLocalDataModel() }

  override suspend fun findUser(userId: String): UserLocalDataModel? =
    userDao.getById(userId)?.toLocalDataModel()

  override suspend fun insertUser(user: UserLocalDataModel) {
    userDao.upsert(user.toEntity())
  }
}

private fun UserEntity.toLocalDataModel() =
  UserLocalDataModel(
    id = id,
    name = name,
    email = email,
    createdAt = Instant.fromEpochMilliseconds(createdAt),
  )

private fun UserLocalDataModel.toEntity() =
  UserEntity(
    id = id,
    name = name,
    email = email,
    createdAt = createdAt.toEpochMilliseconds(),
  )
