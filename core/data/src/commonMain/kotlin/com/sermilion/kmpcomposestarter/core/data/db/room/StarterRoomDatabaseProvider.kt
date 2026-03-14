package com.sermilion.kmpcomposestarter.core.data.db.room

import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.core.data.db.DatabaseProvider
import com.sermilion.kmpcomposestarter.core.data.db.ONBOARDING_DATABASE_FILE_NAME
import com.sermilion.kmpcomposestarter.core.data.db.OnboardingDatabase
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import com.sermilion.kmpcomposestarter.core.data.db.createOnboardingDatabase
import com.sermilion.kmpcomposestarter.core.data.db.createUserDatabase
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class StarterRoomDatabaseProvider(private val builderFactory: PlatformRoomDatabaseBuilderFactory) :
  DatabaseProvider {

  private val databaseCache = mutableMapOf<String, Any>()
  private val cacheLock = SynchronizedObject()

  override fun provideUserDatabase(userId: Uuid): UserDatabase {
    val databaseFileName = userDatabaseFileName(userId)
    return provideDatabase(databaseFileName) {
      createUserDatabase(builderFactory.createUserDatabaseBuilder(databaseFileName))
    }
  }

  override fun provideOnboardingDatabase(): OnboardingDatabase =
    provideDatabase(ONBOARDING_DATABASE_FILE_NAME) {
      createOnboardingDatabase(
        builderFactory.createOnboardingDatabaseBuilder(ONBOARDING_DATABASE_FILE_NAME),
      )
    }

  override fun deleteDatabaseForUser(userId: Uuid) {
    val databaseFileName = userDatabaseFileName(userId)
    synchronized(cacheLock) {
      when (val cachedDatabase = databaseCache.remove(databaseFileName)) {
        is UserDatabase -> cachedDatabase.close()
        is OnboardingDatabase -> cachedDatabase.close()
      }
    }
    try {
      builderFactory.deleteDatabaseFile(databaseFileName)
    } catch (e: Exception) {
      Logger.w(TAG, e) { "Failed to delete database file for user $userId" }
    }
  }

  override fun clearCachedInstances() {
    synchronized(cacheLock) {
      databaseCache.values.forEach { cachedDatabase ->
        when (cachedDatabase) {
          is UserDatabase -> cachedDatabase.close()
          is OnboardingDatabase -> cachedDatabase.close()
        }
      }
      databaseCache.clear()
    }
  }

  @Suppress("UNCHECKED_CAST")
  private fun <T> provideDatabase(cacheKey: String, creator: () -> T): T = synchronized(cacheLock) {
    databaseCache.getOrPut(cacheKey) { creator() as Any } as T
  }

  private companion object {
    const val TAG = "StarterRoomDatabaseProvider"
  }
}

@OptIn(ExperimentalUuidApi::class)
internal fun userDatabaseFileName(userId: Uuid): String = "user_$userId.db"
