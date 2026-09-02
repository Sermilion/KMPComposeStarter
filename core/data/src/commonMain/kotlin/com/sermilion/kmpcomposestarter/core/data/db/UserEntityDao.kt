package com.sermilion.kmpcomposestarter.core.data.db

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * Room's view of the `users` table.
 *
 * One row per database, because each database belongs to one signed-in user - see
 * [com.sermilion.kmpcomposestarter.core.data.db.room.userDatabaseFileName]. Deleting a user's data
 * removes the whole database file rather than issuing a DELETE, so there is no delete query here.
 */
@Dao
interface UserEntityDao {
  @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
  fun observeById(id: String): Flow<UserEntity?>

  @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
  suspend fun getById(id: String): UserEntity?

  @Upsert
  suspend fun upsert(user: UserEntity)
}
