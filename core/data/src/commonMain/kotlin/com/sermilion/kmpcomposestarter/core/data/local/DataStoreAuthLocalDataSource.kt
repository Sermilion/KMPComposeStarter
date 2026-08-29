package com.sermilion.kmpcomposestarter.core.data.local

import androidx.datastore.core.DataStore
import com.sermilion.kmpcomposestarter.core.data.model.AuthTokenDataModel
import com.sermilion.kmpcomposestarter.core.data.model.StoredSession
import com.sermilion.kmpcomposestarter.core.data.model.UserDataModel
import com.sermilion.kmpcomposestarter.core.datastore.PersistedSession
import com.sermilion.kmpcomposestarter.core.datastore.PersistedToken
import com.sermilion.kmpcomposestarter.core.datastore.UserPreferences
import kotlinx.coroutines.flow.first
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

/**
 * Persists the session in the `core:datastore` [UserPreferences] file.
 *
 * The bearer token lands in plain JSON on disk, which is fine for a template with a mock backend
 * and NOT fine for a shipping app: before pointing this at a real backend, move the token to the
 * iOS Keychain, Android `EncryptedFile`/Keystore, and an OS keyring on desktop, and keep only the
 * non-secret session metadata here.
 */
@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DataStoreAuthLocalDataSource(private val dataStore: DataStore<UserPreferences>) :
  AuthLocalDataSource {

  override suspend fun getSession(): StoredSession? =
    dataStore.data.first().session?.toStoredSession()

  override suspend fun saveSession(session: StoredSession) {
    dataStore.updateData { preferences ->
      preferences.copy(session = session.toPersistedSession())
    }
  }

  override suspend fun clearSession(userId: String) {
    dataStore.updateData { preferences ->
      if (preferences.session?.userId == userId) preferences.copy(session = null) else preferences
    }
  }
}

private fun PersistedSession.toStoredSession() = StoredSession(
  user = UserDataModel(id = userId, email = email, name = name),
  token = AuthTokenDataModel(
    accessToken = token.accessToken,
    refreshToken = token.refreshToken,
    expiresAtEpochMillis = token.expiresAtEpochMillis,
  ),
)

private fun StoredSession.toPersistedSession() = PersistedSession(
  userId = user.id,
  email = user.email,
  name = user.name,
  token = PersistedToken(
    accessToken = token.accessToken,
    refreshToken = token.refreshToken,
    expiresAtEpochMillis = token.expiresAtEpochMillis,
  ),
)
