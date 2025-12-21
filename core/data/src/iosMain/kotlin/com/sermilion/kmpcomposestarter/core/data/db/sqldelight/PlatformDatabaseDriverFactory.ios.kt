package com.sermilion.kmpcomposestarter.core.data.db.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.core.data.db.OnboardingDatabase
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import me.tatarka.inject.annotations.Inject
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@OptIn(ExperimentalForeignApi::class)
@Inject
@SingleIn(AppScope::class)
actual class PlatformDatabaseDriverFactory {

  actual fun createUserDatabaseDriver(databaseName: String): SqlDriver = NativeSqliteDriver(
    schema = UserDatabase.Schema,
    name = "$databaseName.db",
    maxReaderConnections = 4,
  )

  actual fun createOnboardingDatabaseDriver(databaseName: String): SqlDriver = NativeSqliteDriver(
    schema = OnboardingDatabase.Schema,
    name = "$databaseName.db",
    maxReaderConnections = 4,
  )

  actual fun deleteDatabaseFile(databaseName: String) {
    try {
      val fileManager = NSFileManager.defaultManager
      val dbPath = getDatabasePath(databaseName)
      val dbUrl = NSURL.fileURLWithPath(dbPath)
      fileManager.removeItemAtURL(dbUrl, null)
    } catch (e: Exception) {
      Logger.w("PlatformDatabaseDriverFactory") { "Failed to delete database file: $databaseName" }
    }
  }

  private fun getDatabasePath(databaseName: String): String {
    val fileManager = NSFileManager.defaultManager
    val documentDirectory = fileManager.URLsForDirectory(
      directory = NSDocumentDirectory,
      inDomains = NSUserDomainMask,
    ).first() as NSURL
    return "${documentDirectory.path}/$databaseName.db"
  }
}
