package com.sermilion.kmpcomposestarter.core.data.db

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor

@Database(
  entities = [UserEntity::class],
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

/**
 * Migration policy: every schema change bumps [UserDatabase]'s version, ships a numbered
 * `Migration`, and re-exports the schema under `core/data/schemas/`. Never reach for
 * `fallbackToDestructiveMigration` — it deletes the user's data to make a build compile.
 *
 * Template for the first schema change, to be uncommented and registered below:
 *
 * ```
 * val MIGRATION_1_2 = object : Migration(1, 2) {
 *   override fun migrate(connection: SQLiteConnection) {
 *     connection.execSQL("ALTER TABLE users ADD COLUMN avatarUrl TEXT")
 *   }
 * }
 * ```
 */
fun createUserDatabase(builder: RoomDatabase.Builder<UserDatabase>): UserDatabase =
  builder
    .build()
