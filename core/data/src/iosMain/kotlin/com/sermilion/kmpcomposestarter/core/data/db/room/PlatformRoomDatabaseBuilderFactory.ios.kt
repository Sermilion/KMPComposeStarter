package com.sermilion.kmpcomposestarter.core.data.db.room

import androidx.room3.RoomDatabase
import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.core.data.db.OnboardingDatabase
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import com.sermilion.kmpcomposestarter.core.data.db.databaseFileUrl
import kotlinx.cinterop.ExperimentalForeignApi
import me.tatarka.inject.annotations.Inject
import platform.Foundation.NSFileManager
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import com.sermilion.kmpcomposestarter.core.data.db.createOnboardingDatabaseBuilder as newOnboardingDatabaseBuilder
import com.sermilion.kmpcomposestarter.core.data.db.createUserDatabaseBuilder as newUserDatabaseBuilder

@OptIn(ExperimentalForeignApi::class)
@Inject
@SingleIn(AppScope::class)
actual class PlatformRoomDatabaseBuilderFactory {
  actual fun createUserDatabaseBuilder(
    databaseFileName: String,
  ): RoomDatabase.Builder<UserDatabase> = newUserDatabaseBuilder(databaseFileName)

  actual fun createOnboardingDatabaseBuilder(
    databaseFileName: String,
  ): RoomDatabase.Builder<OnboardingDatabase> = newOnboardingDatabaseBuilder(databaseFileName)

  actual fun deleteDatabaseFile(databaseFileName: String) {
    try {
      NSFileManager.defaultManager.removeItemAtURL(databaseFileUrl(databaseFileName), null)
    } catch (e: Exception) {
      Logger.w("PlatformRoomDatabaseBuilderFactory") {
        "Failed to delete database file: $databaseFileName"
      }
    }
  }
}
