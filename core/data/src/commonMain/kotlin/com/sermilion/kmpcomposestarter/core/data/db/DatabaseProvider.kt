package com.sermilion.kmpcomposestarter.core.data.db

/**
 * Opens and closes the per-user databases. Instances are cached per user id, so the session that
 * asked for a database and the teardown that closes it are talking about the same object.
 */
interface DatabaseProvider {

  fun provideUserDatabase(userId: String): UserDatabase

  /** Closes and forgets [userId]'s cached instance. Safe to call when nothing was opened. */
  fun closeDatabaseForUser(userId: String)

  /**
   * Closes [userId]'s database and removes its files, sidecars included. Returns whether every
   * file is gone; callers must not ignore a `false`, which means user data survived a deletion.
   *
   * The close happens before any file is touched and the instance is never reopened, so this call
   * is terminal for the session's data reads whichever way it returns. On `false` the session
   * stays signed in — reporting success over surviving data would be worse — but it can no longer
   * read its own database, and callers must not treat it as if it could.
   */
  fun deleteDatabaseForUser(userId: String): Boolean
}
