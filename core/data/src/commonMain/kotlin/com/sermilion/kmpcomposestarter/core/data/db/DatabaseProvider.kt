package com.sermilion.kmpcomposestarter.core.data.db

import app.cash.sqldelight.db.SqlDriver
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface DatabaseProvider {
  fun provideUserDatabase(userId: Uuid): UserDatabase
  fun provideUserDatabaseDriver(userId: Uuid): SqlDriver
  fun provideOnboardingDatabase(): OnboardingDatabase
  fun deleteDatabaseForUser(userId: Uuid)
  fun clearCachedInstances()
}
