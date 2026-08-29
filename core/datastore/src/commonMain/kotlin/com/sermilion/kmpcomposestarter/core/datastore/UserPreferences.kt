package com.sermilion.kmpcomposestarter.core.datastore

import kotlinx.serialization.Serializable

/**
 * Everything the app keeps about the signed-in user between process launches.
 *
 * Identity and credentials live inside one nullable [session] so that signing a different user in,
 * or signing out, is a single atomic write. No launch can observe one user's id next to another
 * user's token, and no partial session can survive a crash mid-write.
 */
@Serializable
data class UserPreferences(
  val session: PersistedSession? = null,
)

/** The signed-in user plus the credentials that authorize their requests. */
@Serializable
data class PersistedSession(
  val userId: String,
  val email: String,
  val name: String,
  val token: PersistedToken,
)

@Serializable
data class PersistedToken(
  val accessToken: String,
  val refreshToken: String? = null,
  val expiresAtEpochMillis: Long? = null,
)
