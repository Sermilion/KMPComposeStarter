package com.sermilion.kmpcomposestarter.core.datastore

import androidx.datastore.core.DataStore
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import okio.Path
import okio.Path.Companion.toOkioPath
import java.nio.file.Files

private val storedSession = PersistedSession(
  userId = "user-a",
  email = "a@test.com",
  name = "User A",
  token = PersistedToken(
    accessToken = "access-a",
    refreshToken = "refresh-a",
    expiresAtEpochMillis = 1_234L,
  ),
)

class UserPreferencesDataStoreTest :
  FunSpec({

    test("a session written through the store is readable by a fresh store over the same file") {
      val directory = Files.createTempDirectory("user-preferences").toFile()
      val path = directory.resolve(USER_PREFERENCES_FILE_NAME).toOkioPath()

      try {
        withUserPreferencesStore(path) { store ->
          store.updateData { it.copy(session = storedSession) }
        }

        withUserPreferencesStore(path) { store ->
          store.data.first().session shouldBe storedSession
        }
      } finally {
        directory.deleteRecursively()
      }
    }
  })

/**
 * Runs [block] against a store that owns [path], then releases the file so the next store can
 * claim it — which is what makes "survives a fresh instance" a real assertion.
 */
private suspend fun withUserPreferencesStore(
  path: Path,
  block: suspend (DataStore<UserPreferences>) -> Unit,
) {
  val job = SupervisorJob()
  try {
    block(createUserPreferencesDataStore(CoroutineScope(job + Dispatchers.IO)) { path })
  } finally {
    job.cancel()
    job.join()
  }
}
