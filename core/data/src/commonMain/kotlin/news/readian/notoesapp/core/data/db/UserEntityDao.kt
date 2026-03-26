package news.readian.notoesapp.core.data.db

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserEntityDao {
  @Query("SELECT * FROM users ORDER BY createdAt DESC")
  fun observeUsers(): Flow<List<UserDataModel>>

  @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
  fun observeById(id: String): Flow<UserDataModel?>

  @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
  suspend fun getById(id: String): UserDataModel?

  @Upsert
  suspend fun upsert(user: UserDataModel)

  @Upsert
  suspend fun upsertAll(users: List<UserDataModel>)

  @Query("DELETE FROM users WHERE id = :id")
  suspend fun deleteById(id: String)

  @Query("DELETE FROM users")
  suspend fun deleteAll()
}
