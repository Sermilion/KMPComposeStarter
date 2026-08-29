package com.sermilion.kmpcomposestarter.core.data.db

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File
import kotlin.coroutines.CoroutineContext

fun createUserDatabaseBuilder(
  databaseFileName: String,
  queryContext: CoroutineContext,
  path: String = defaultDatabasePath(databaseFileName),
): RoomDatabase.Builder<UserDatabase> = Room.databaseBuilder<UserDatabase>(
  name = path,
  factory = UserDatabaseConstructor::initialize,
)
  .setDriver(BundledSQLiteDriver())
  .setQueryCoroutineContext(queryContext)

internal fun defaultDatabasePath(databaseFileName: String): String =
  File(desktopDataDirectory(), databaseFileName).absolutePath

/**
 * Desktop data directory, shared with the `core:datastore` preferences file.
 *
 * `-Dstarter.dataDir=...` overrides it so tests and portable installs never write into the
 * developer's home directory.
 */
private fun desktopDataDirectory(): File {
  val directory = System.getProperty("starter.dataDir")
    ?.let(::File)
    ?: File(System.getProperty("user.home"), ".kmpcomposestarter")
  directory.mkdirs()
  return directory
}
