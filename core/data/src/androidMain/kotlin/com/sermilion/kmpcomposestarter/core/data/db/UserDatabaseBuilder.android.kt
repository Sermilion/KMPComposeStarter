package com.sermilion.kmpcomposestarter.core.data.db

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun createUserDatabaseBuilder(
  context: Context,
  databaseFileName: String,
  path: String = context.applicationContext.getDatabasePath(databaseFileName).absolutePath,
): RoomDatabase.Builder<UserDatabase> {
  val appContext = context.applicationContext

  return Room.databaseBuilder<UserDatabase>(
    context = appContext,
    name = path,
    factory = UserDatabaseConstructor::initialize,
  ).setDriver(BundledSQLiteDriver())
}
