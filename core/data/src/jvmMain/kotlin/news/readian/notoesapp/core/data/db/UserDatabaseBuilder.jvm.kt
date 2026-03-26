package news.readian.notoesapp.core.data.db

import androidx.room3.Room
import androidx.room3.RoomDatabase
import java.io.File

fun createUserDatabaseBuilder(
  databaseFileName: String,
  path: String = defaultDatabasePath(databaseFileName),
): RoomDatabase.Builder<UserDatabase> = Room.databaseBuilder<UserDatabase>(
  name = path,
  factory = UserDatabaseConstructor::initialize,
).setDriver(JdbcSqliteDriver())

fun createOnboardingDatabaseBuilder(
  databaseFileName: String = ONBOARDING_DATABASE_FILE_NAME,
  path: String = defaultDatabasePath(databaseFileName),
): RoomDatabase.Builder<OnboardingDatabase> = Room.databaseBuilder<OnboardingDatabase>(
  name = path,
  factory = OnboardingDatabaseConstructor::initialize,
).setDriver(JdbcSqliteDriver())

internal fun defaultDatabasePath(databaseFileName: String): String {
  val appDir = File(System.getProperty("user.home"), ".kmpcomposestarter")
  appDir.mkdirs()
  return File(appDir, databaseFileName).absolutePath
}
