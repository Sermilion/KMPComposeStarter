package com.sermilion.kmpcomposestarter.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

/**
 * JSON codec for [UserPreferences].
 *
 * Unreadable bytes are reported as a [CorruptionException] rather than propagating a
 * serialization failure, which is what lets the store's corruption handler recover instead of
 * failing every read for the rest of the process.
 */
internal object UserPreferencesSerializer : OkioSerializer<UserPreferences> {

  private val json = Json { ignoreUnknownKeys = true }

  override val defaultValue: UserPreferences = UserPreferences()

  override suspend fun readFrom(source: BufferedSource): UserPreferences = try {
    json.decodeFromString(UserPreferences.serializer(), source.readUtf8())
  } catch (e: SerializationException) {
    throw CorruptionException("Stored user preferences could not be read", e)
  }

  override suspend fun writeTo(t: UserPreferences, sink: BufferedSink) {
    sink.writeUtf8(json.encodeToString(UserPreferences.serializer(), t))
  }
}
