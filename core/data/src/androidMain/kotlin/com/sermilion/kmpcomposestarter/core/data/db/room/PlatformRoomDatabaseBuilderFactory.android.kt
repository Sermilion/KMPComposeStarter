package com.sermilion.kmpcomposestarter.core.data.db.room

import android.app.Application
import androidx.room3.RoomDatabase
import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import com.sermilion.kmpcomposestarter.core.data.db.databaseFilePaths
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import java.io.File
import kotlin.coroutines.CoroutineContext
import com.sermilion.kmpcomposestarter.core.data.db.createUserDatabaseBuilder as newUserDatabaseBuilder

@Inject
@SingleIn(AppScope::class)
actual class PlatformRoomDatabaseBuilderFactory(private val application: Application) {

  actual fun createUserDatabaseBuilder(
    databaseFileName: String,
    queryContext: CoroutineContext,
  ): RoomDatabase.Builder<UserDatabase> = newUserDatabaseBuilder(
    context = application,
    databaseFileName = databaseFileName,
    queryContext = queryContext,
  )

  actual fun deleteDatabaseFile(databaseFileName: String): Boolean {
    val databasePath = application.getDatabasePath(databaseFileName).absolutePath
    return databaseFilePaths(databasePath)
      .map { path -> deleteIfPresent(File(path)) }
      .all { it }
  }

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
