package com.sermilion.kmpcomposestarter.core.data.db.room

import androidx.room3.RoomDatabase
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase

expect class PlatformRoomDatabaseBuilderFactory {
  fun createUserDatabaseBuilder(databaseFileName: String): RoomDatabase.Builder<UserDatabase>
  fun deleteDatabaseFile(databaseFileName: String)
}
