package com.sermilion.kmpcomposestarter.core.data.db.sqldelight

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.sermilion.kmpcomposestarter.core.data.db.OnboardingDatabase
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
actual class PlatformDatabaseDriverFactory(private val application: Application) {

  actual fun createUserDatabaseDriver(databaseName: String): SqlDriver = AndroidSqliteDriver(
    schema = UserDatabase.Schema,
    context = application,
    name = "$databaseName.db",
  )

  actual fun createOnboardingDatabaseDriver(databaseName: String): SqlDriver = AndroidSqliteDriver(
    schema = OnboardingDatabase.Schema,
    context = application,
    name = "$databaseName.db",
  )

  actual fun deleteDatabaseFile(databaseName: String) {
    application.deleteDatabase("$databaseName.db")
  }
}
