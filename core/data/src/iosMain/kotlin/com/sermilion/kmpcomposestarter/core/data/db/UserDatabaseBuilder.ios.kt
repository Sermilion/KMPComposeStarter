package com.sermilion.kmpcomposestarter.core.data.db

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

fun createUserDatabaseBuilder(
  databaseFileName: String,
  path: String = defaultDatabasePath(databaseFileName),
): RoomDatabase.Builder<UserDatabase> = Room.databaseBuilder<UserDatabase>(
  name = path,
  factory = UserDatabaseConstructor::initialize,
).setDriver(BundledSQLiteDriver())

fun createOnboardingDatabaseBuilder(
  databaseFileName: String = ONBOARDING_DATABASE_FILE_NAME,
  path: String = defaultDatabasePath(databaseFileName),
): RoomDatabase.Builder<OnboardingDatabase> = Room.databaseBuilder<OnboardingDatabase>(
  name = path,
  factory = OnboardingDatabaseConstructor::initialize,
).setDriver(BundledSQLiteDriver())

@OptIn(ExperimentalForeignApi::class)
internal fun defaultDatabasePath(databaseFileName: String): String {
  val documentDirectory =
    NSFileManager.defaultManager.URLForDirectory(
      directory = NSDocumentDirectory,
      inDomain = NSUserDomainMask,
      appropriateForURL = null,
      create = false,
      error = null,
    )

  return requireNotNull(documentDirectory?.path) + "/$databaseFileName"
}

internal fun databaseFileUrl(databaseFileName: String): NSURL =
  NSURL.fileURLWithPath(defaultDatabasePath(databaseFileName))
