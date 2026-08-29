package com.sermilion.kmpcomposestarter.core.data.db

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlin.coroutines.CoroutineContext

fun createUserDatabaseBuilder(
  context: Context,
  databaseFileName: String,
  queryContext: CoroutineContext,
  path: String = context.applicationContext.getDatabasePath(databaseFileName).absolutePath,
): RoomDatabase.Builder<UserDatabase> = Room.databaseBuilder<UserDatabase>(
  context = context.applicationContext,
  name = path,
  factory = UserDatabaseConstructor::initialize,
)
  .setDriver(BundledSQLiteDriver())
  .setQueryCoroutineContext(queryContext)
