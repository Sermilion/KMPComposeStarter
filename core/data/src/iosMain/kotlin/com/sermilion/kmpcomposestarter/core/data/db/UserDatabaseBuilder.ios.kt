package com.sermilion.kmpcomposestarter.core.data.db

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
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

@OptIn(ExperimentalForeignApi::class)
internal fun defaultDatabasePath(databaseFileName: String): String {
  val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
    directory = NSDocumentDirectory,
    inDomain = NSUserDomainMask,
    appropriateForURL = null,
    create = true,
    error = null,
  )

  return "${requireNotNull(documentDirectory?.path)}/$databaseFileName"
}
