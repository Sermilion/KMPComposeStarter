package com.sermilion.kmpcomposestarter.core.data.mapper

import com.sermilion.kmpcomposestarter.core.data.model.UserLocalDataModel
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlin.time.Instant

/**
 * [createdAt] is a parameter, not a `Clock.System.now()` call inside the mapper: a mapping that
 * reads the clock is not a mapping, and it makes the same input produce a different row every
 * time it runs — including on every re-login.
 */
fun UserData.toLocalDataModel(createdAt: Instant): UserLocalDataModel =
  UserLocalDataModel(
    id = id,
    name = name,
    email = email,
    createdAt = createdAt,
  )

fun UserLocalDataModel.toDomainModel(): UserData =
  UserData(
    id = id,
    email = email.orEmpty(),
    name = name,
  )
