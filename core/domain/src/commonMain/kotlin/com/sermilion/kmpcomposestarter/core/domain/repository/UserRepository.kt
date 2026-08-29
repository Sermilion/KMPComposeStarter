package com.sermilion.kmpcomposestarter.core.domain.repository

import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.flow.Flow

/**
 * The signed-in user's own record, read from and written to that user's local database.
 *
 * Session-scoped: an instance belongs to one user and can never reach another user's data.
 */
interface UserRepository {
  /** Emits the persisted row for this session's user, or `null` while none is stored. */
  fun observeCurrentUser(): Flow<UserData?>

  /** Stores the user's row, preserving the original creation time on subsequent sign-ins. */
  suspend fun saveUser(user: UserData)

  /**
   * Deletes this user's local database and its SQLite sidecar files. Returns whether every file
   * is gone; callers sign the user out afterwards, because the session's data no longer exists.
   */
  suspend fun deleteMyData(): Boolean
}
