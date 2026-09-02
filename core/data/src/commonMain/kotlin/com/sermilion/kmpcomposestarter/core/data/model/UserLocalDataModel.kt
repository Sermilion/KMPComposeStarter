package com.sermilion.kmpcomposestarter.core.data.model

import kotlinx.datetime.Instant

/**
 * A stored user as the rest of the data layer sees it.
 *
 * [id] stays the opaque string the backend issued: forcing it into a UUID would reject perfectly
 * valid server ids at the persistence boundary.
 */
data class UserLocalDataModel(
  val id: String,
  val name: String,
  val email: String?,
  val createdAt: Instant,
)
