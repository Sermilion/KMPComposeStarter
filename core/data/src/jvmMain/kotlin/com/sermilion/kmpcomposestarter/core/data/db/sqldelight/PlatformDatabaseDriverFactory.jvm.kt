package com.sermilion.kmpcomposestarter.core.data.db.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.core.data.db.OnboardingDatabase
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import java.io.File

@Inject
@SingleIn(AppScope::class)
actual class PlatformDatabaseDriverFactory {

  actual fun createUserDatabaseDriver(databaseName: String): SqlDriver {
    Class.forName("org.sqlite.JDBC")
    val dbPath = getDatabasePath(databaseName)
    val dbFile = File(dbPath)
    val databaseExists = dbFile.exists()

    val driver = JdbcSqliteDriver("jdbc:sqlite:$dbPath")

    if (!databaseExists) {
      UserDatabase.Schema.create(driver)
    }
    return driver
  }

  actual fun createOnboardingDatabaseDriver(databaseName: String): SqlDriver {
    Class.forName("org.sqlite.JDBC")
    val dbPath = getDatabasePath(databaseName)
    val dbFile = File(dbPath)
    val databaseExists = dbFile.exists()

    val driver = JdbcSqliteDriver("jdbc:sqlite:$dbPath")

    if (!databaseExists) {
      OnboardingDatabase.Schema.create(driver)
    }
    return driver
  }

  actual fun deleteDatabaseFile(databaseName: String) {
    try {
      val dbPath = getDatabasePath(databaseName)
      val dbFile = File(dbPath)
      if (dbFile.exists()) {
        dbFile.delete()
      }
    } catch (e: Exception) {
      Logger.w("PlatformDatabaseDriverFactory") { "Failed to delete database file: $databaseName" }
    }
  }

  private fun getDatabasePath(databaseName: String): String {
    val userHome = System.getProperty("user.home")
    val appDir = File(userHome, ".kmpcomposestarter")
    appDir.mkdirs()
    return File(appDir, "$databaseName.db").absolutePath
  }
}
