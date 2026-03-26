package news.readian.notoesapp.core.data.db

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
@Database(
  entities = [UserDataModel::class],
  version = 1,
  exportSchema = true,
)
@ConstructedBy(UserDatabaseConstructor::class)
abstract class UserDatabase : RoomDatabase() {
  abstract fun userEntityDao(): UserEntityDao
}

@Suppress("KotlinNoActualForExpect")
expect object UserDatabaseConstructor : RoomDatabaseConstructor<UserDatabase> {
  override fun initialize(): UserDatabase
}

fun createUserDatabase(builder: RoomDatabase.Builder<UserDatabase>): UserDatabase = builder.build()
