package com.sermilion.kmpcomposestarter.core.data.db.room

import androidx.room3.RoomDatabase
import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import com.sermilion.kmpcomposestarter.core.data.db.databaseFilePaths
import com.sermilion.kmpcomposestarter.core.data.db.defaultDatabasePath
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import java.io.File
import kotlin.coroutines.CoroutineContext
import com.sermilion.kmpcomposestarter.core.data.db.createUserDatabaseBuilder as newUserDatabaseBuilder

@Inject
@SingleIn(AppScope::class)
actual class PlatformRoomDatabaseBuilderFactory {

  actual fun createUserDatabaseBuilder(
    databaseFileName: String,
    queryContext: CoroutineContext,
  ): RoomDatabase.Builder<UserDatabase> = newUserDatabaseBuilder(
    databaseFileName = databaseFileName,
    queryContext = queryContext,
  )

  actual fun deleteDatabaseFile(databaseFileName: String): Boolean =
    databaseFilePaths(defaultDatabasePath(databaseFileName))
      .map { path -> deleteIfPresent(File(path)) }
      .all { it }

  private fun deleteIfPresent(file: File): Boolean {
    val deleted = !file.exists() || file.delete()
    if (!deleted) {
      Logger.w(TAG) { "Could not delete database file ${file.name}" }
    }
    return deleted
  }

  private companion object {
    const val TAG = "PlatformRoomDatabaseBuilderFactory"
  }
}
