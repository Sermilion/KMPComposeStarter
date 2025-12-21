package com.sermilion.kmpcomposestarter.core.data.model

import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class UserLocalDataModel(
  val id: Uuid,
  val name: String,
  val email: String?,
  val createdAt: Instant,
)
