package com.sermilion.kmpcomposestarter.core.data.db.sqldelight

import app.cash.sqldelight.db.SqlDriver
import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.core.data.db.DatabaseProvider
import com.sermilion.kmpcomposestarter.core.data.db.OnboardingDatabase
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
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
class StarterSqlDelightDatabaseProvider(private val driverFactory: PlatformDatabaseDriverFactory) :
  DatabaseProvider {

  private val databaseCache = mutableMapOf<String, Any>()
  private val driverCache = mutableMapOf<String, SqlDriver>()
  private val cacheLock = SynchronizedObject()

  override fun provideUserDatabase(userId: Uuid): UserDatabase {
    val databaseName = "$USER_DATABASE_NAME_PREFIX$userId"
    return provideDatabase(databaseName) {
      val driver = provideUserDatabaseDriver(userId)
      UserDatabase(driver)
    }
  }

  override fun provideUserDatabaseDriver(userId: Uuid): SqlDriver {
    val databaseName = "$USER_DATABASE_NAME_PREFIX$userId"
    return synchronized(cacheLock) {
      driverCache.getOrPut(databaseName) {
        driverFactory.createUserDatabaseDriver(databaseName)
      }
    }
  }

  override fun provideOnboardingDatabase(): OnboardingDatabase =
    provideDatabase(ONBOARDING_DATABASE_NAME) {
      val driver = driverFactory.createOnboardingDatabaseDriver(ONBOARDING_DATABASE_NAME)
      OnboardingDatabase(driver)
    }

  override fun deleteDatabaseForUser(userId: Uuid) {
    val databaseName = "$USER_DATABASE_NAME_PREFIX$userId"
    synchronized(cacheLock) {
      databaseCache.remove(databaseName)
      driverCache.remove(databaseName)?.close()
    }
    try {
      driverFactory.deleteDatabaseFile(databaseName)
    } catch (e: Exception) {
      Logger.w(TAG, e) { "Failed to delete database file for user $userId" }
    }
  }

  override fun clearCachedInstances() {
    synchronized(cacheLock) {
      databaseCache.clear()
      driverCache.values.forEach { it.close() }
      driverCache.clear()
    }
  }

  @Suppress("UNCHECKED_CAST")
  private fun <T> provideDatabase(databaseName: String, creator: () -> T): T =
    synchronized(cacheLock) {
      databaseCache.getOrPut(databaseName) { creator() as Any } as T
    }

  private companion object {
    const val TAG = "StarterSqlDelightDatabaseProvider"
    const val USER_DATABASE_NAME_PREFIX = "user_"
    const val ONBOARDING_DATABASE_NAME = "onboarding"
  }
}
