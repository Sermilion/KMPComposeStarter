package com.sermilion.kmpcomposestarter.core.data.db.room

import android.app.Application
import androidx.room3.RoomDatabase
import com.sermilion.kmpcomposestarter.core.data.db.OnboardingDatabase
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import com.sermilion.kmpcomposestarter.core.data.db.createOnboardingDatabaseBuilder as newOnboardingDatabaseBuilder
import com.sermilion.kmpcomposestarter.core.data.db.createUserDatabaseBuilder as newUserDatabaseBuilder

@Inject
@SingleIn(AppScope::class)
actual class PlatformRoomDatabaseBuilderFactory(private val application: Application) {
  actual fun createUserDatabaseBuilder(
    databaseFileName: String,
  ): RoomDatabase.Builder<UserDatabase> = newUserDatabaseBuilder(
    context = application,
    databaseFileName = databaseFileName,
  )

  actual fun createOnboardingDatabaseBuilder(
    databaseFileName: String,
  ): RoomDatabase.Builder<OnboardingDatabase> = newOnboardingDatabaseBuilder(
    context = application,
    databaseFileName = databaseFileName,
  )

  actual fun deleteDatabaseFile(databaseFileName: String) {
    application.deleteDatabase(databaseFileName)
  }
}
