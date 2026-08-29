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
   */
  fun deleteDatabaseForUser(userId: String): Boolean
}
