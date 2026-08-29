package com.sermilion.kmpcomposestarter.core.data.repository

import com.sermilion.kmpcomposestarter.common.coroutines.DispatcherProvider
import com.sermilion.kmpcomposestarter.common.di.UserScope
import com.sermilion.kmpcomposestarter.core.data.db.DatabaseProvider
import com.sermilion.kmpcomposestarter.core.data.db.dao.UserDao
import com.sermilion.kmpcomposestarter.core.data.mapper.toDomainModel
import com.sermilion.kmpcomposestarter.core.data.mapper.toLocalDataModel
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(UserScope::class)
class StarterUserRepository(
  private val userDao: UserDao,
  private val userData: UserData,
  private val databaseProvider: DatabaseProvider,
  private val dispatcherProvider: DispatcherProvider,
) : UserRepository {

  override fun observeCurrentUser(): Flow<UserData?> =
    userDao.observeUser(userData.id).map { it?.toDomainModel() }

  /**
   * Reads the existing row first so a returning user keeps their original creation time. The
   * clock is read here, at the write, and handed to the mapper — mapping itself stays pure.
   */
  override suspend fun saveUser(user: UserData) {
    val storedCreatedAt = userDao.findUser(user.id)?.createdAt
    userDao.insertUser(user.toLocalDataModel(createdAt = storedCreatedAt ?: Clock.System.now()))
  }

  override suspend fun deleteMyData(): Boolean = withContext(dispatcherProvider.io) {
    databaseProvider.deleteDatabaseForUser(userData.id)
  }
}
