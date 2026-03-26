package news.readian.notoesapp.core.data.db

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun createOnboardingDatabaseBuilder(
  context: Context,
  databaseFileName: String = ONBOARDING_DATABASE_FILE_NAME,
  path: String = context.applicationContext.getDatabasePath(databaseFileName).absolutePath,
): RoomDatabase.Builder<OnboardingDatabase> {
  val appContext = context.applicationContext

  return Room.databaseBuilder<OnboardingDatabase>(
    context = appContext,
    name = path,
    factory = OnboardingDatabaseConstructor::initialize,
  ).setDriver(BundledSQLiteDriver())
}
