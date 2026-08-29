package com.sermilion.kmpcomposestarter.core.data.db.room

import androidx.room3.RoomDatabase
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import kotlin.coroutines.CoroutineContext

expect class PlatformRoomDatabaseBuilderFactory {

  fun createUserDatabaseBuilder(
    databaseFileName: String,
    queryContext: CoroutineContext,
  ): RoomDatabase.Builder<UserDatabase>

  /** Removes the database file and its `-wal`/`-shm` sidecars. Returns whether all are gone. */
  fun deleteDatabaseFile(databaseFileName: String): Boolean
}
