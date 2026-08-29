package com.sermilion.kmpcomposestarter.core.data.db.room

import androidx.room3.RoomDatabase
import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import com.sermilion.kmpcomposestarter.core.data.db.databaseFilePaths
import com.sermilion.kmpcomposestarter.core.data.db.defaultDatabasePath
import kotlinx.cinterop.ExperimentalForeignApi
import me.tatarka.inject.annotations.Inject
import platform.Foundation.NSFileManager
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.coroutines.CoroutineContext
import com.sermilion.kmpcomposestarter.core.data.db.createUserDatabaseBuilder as newUserDatabaseBuilder

@OptIn(ExperimentalForeignApi::class)
@Inject
@SingleIn(AppScope::class)
actual class PlatformRoomDatabaseBuilderFactory {
  actual fun createUserDatabaseBuilder(
    databaseFileName: String,
    queryContext: CoroutineContext,
  ): RoomDatabase.Builder<UserDatabase> =
    newUserDatabaseBuilder(
      databaseFileName = databaseFileName,
      queryContext = queryContext,
    )

  actual fun deleteDatabaseFile(databaseFileName: String): Boolean =
    databaseFilePaths(defaultDatabasePath(databaseFileName))
      .map(::deleteIfPresent)
      .all { it }

  private fun deleteIfPresent(path: String): Boolean {
    val fileManager = NSFileManager.defaultManager
    if (!fileManager.fileExistsAtPath(path)) return true

    val deleted = fileManager.removeItemAtPath(path, null)
    if (!deleted) {
      Logger.w(TAG) { "Could not delete database file ${path.substringAfterLast('/')}" }
    }
    return deleted
  }

  private companion object {
    const val TAG = "PlatformRoomDatabaseBuilderFactory"
  }
}
