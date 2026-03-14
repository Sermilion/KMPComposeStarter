package com.sermilion.kmpcomposestarter.core.data.db

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
internal const val ONBOARDING_DATABASE_FILE_NAME = "onboarding.db"

@Database(
  entities = [OnboardingTagDataModel::class],
  version = 1,
  exportSchema = true,
)
@ConstructedBy(OnboardingDatabaseConstructor::class)
abstract class OnboardingDatabase : RoomDatabase() {
  abstract fun onboardingTagEntityDao(): OnboardingTagEntityDao
}

@Suppress("KotlinNoActualForExpect")
expect object OnboardingDatabaseConstructor : RoomDatabaseConstructor<OnboardingDatabase> {
  override fun initialize(): OnboardingDatabase
}

fun createOnboardingDatabase(
  builder: RoomDatabase.Builder<OnboardingDatabase>,
): OnboardingDatabase = builder.build()
