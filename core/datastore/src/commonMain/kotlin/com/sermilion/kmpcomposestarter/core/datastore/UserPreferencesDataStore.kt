package com.sermilion.kmpcomposestarter.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.core.okio.OkioStorage
import kotlinx.coroutines.CoroutineScope
import okio.FileSystem
import okio.Path

/**
 * File name of the single [UserPreferences] store.
 *
 * Renaming it strands every existing user's session, so treat a rename as a migration and ship a
 * [androidx.datastore.core.DataMigration] alongside it.
 */
const val USER_PREFERENCES_FILE_NAME: String = "user_preferences.json"

/**
 * Builds the [UserPreferences] store over [producePath].
 *
 * Kept separate from the DI module so tests can point the same store at a temporary file, and so
 * the caller owns [scope] — cancelling it releases the file.
 */
fun createUserPreferencesDataStore(
  scope: CoroutineScope,
  producePath: () -> Path,
): DataStore<UserPreferences> =
  DataStoreFactory.create(
    storage =
      OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = UserPreferencesSerializer,
        producePath = producePath,
      ),
    corruptionHandler = ReplaceFileCorruptionHandler { UserPreferences() },
    scope = scope,
  )
