package com.sermilion.kmpcomposestarter.core.data.db

/**
 * Every file that belongs to one SQLite database.
 *
 * In WAL mode a database is three files, and committed pages can still be sitting in the `-wal`
 * companion. Deleting only the main file would leave the previous user's rows recoverable, so
 * every platform's deletion path walks this list.
 */
internal fun databaseFilePaths(databasePath: String): List<String> =
  listOf(databasePath, "$databasePath-wal", "$databasePath-shm")
