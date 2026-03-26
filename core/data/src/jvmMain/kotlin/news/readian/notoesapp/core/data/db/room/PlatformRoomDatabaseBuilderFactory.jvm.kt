package news.readian.notoesapp.core.data.db.room

import androidx.room3.RoomDatabase
import co.touchlab.kermit.Logger
import me.tatarka.inject.annotations.Inject
import news.readian.notoesapp.core.data.db.OnboardingDatabase
import news.readian.notoesapp.core.data.db.UserDatabase
import news.readian.notoesapp.core.data.db.defaultDatabasePath
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import java.io.File
import news.readian.notoesapp.core.data.db.createOnboardingDatabaseBuilder as newOnboardingDatabaseBuilder
import news.readian.notoesapp.core.data.db.createUserDatabaseBuilder as newUserDatabaseBuilder

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
      File(defaultDatabasePath(databaseFileName)).delete()
    } catch (e: Exception) {
      Logger.w("PlatformRoomDatabaseBuilderFactory") {
        "Failed to delete database file: $databaseFileName"
      }
    }
  }
}
