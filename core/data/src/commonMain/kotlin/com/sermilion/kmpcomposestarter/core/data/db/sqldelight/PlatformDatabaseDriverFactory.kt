package com.sermilion.kmpcomposestarter.core.data.db.sqldelight

import app.cash.sqldelight.db.SqlDriver

expect class PlatformDatabaseDriverFactory {
  fun createUserDatabaseDriver(databaseName: String): SqlDriver
  fun createOnboardingDatabaseDriver(databaseName: String): SqlDriver
  fun deleteDatabaseFile(databaseName: String)
}
