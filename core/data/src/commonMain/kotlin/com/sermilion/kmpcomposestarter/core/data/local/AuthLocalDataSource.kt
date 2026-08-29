package com.sermilion.kmpcomposestarter.core.data.local

import com.sermilion.kmpcomposestarter.core.data.model.StoredSession

/**
 * The single durable home of the signed-in session. There is no in-memory copy to fall out of
 * sync with it.
 */
interface AuthLocalDataSource {

  suspend fun getSession(): StoredSession?

  /** Replaces whatever session is stored — identity and token together, in one write. */
  suspend fun saveSession(session: StoredSession)

  /**
   * Clears the stored session only when it belongs to [userId], so a teardown arriving late from
   * a replaced session can never erase the session that replaced it.
   */
  suspend fun clearSession(userId: String)
}
