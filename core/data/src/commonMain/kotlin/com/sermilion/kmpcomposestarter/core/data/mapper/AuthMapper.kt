package com.sermilion.kmpcomposestarter.core.data.mapper

import com.sermilion.kmpcomposestarter.core.data.model.AuthTokenDataModel
import com.sermilion.kmpcomposestarter.core.data.model.UserDataModel
import com.sermilion.kmpcomposestarter.core.domain.auth.AuthToken
import com.sermilion.kmpcomposestarter.core.domain.model.UserData

fun UserDataModel.toDomainModel(): UserData = UserData(id = id, email = email, name = name)

fun UserData.toDataModel(): UserDataModel = UserDataModel(id = id, email = email, name = name)

fun AuthTokenDataModel.toDomainModel(): AuthToken = AuthToken(
  accessToken = accessToken,
  refreshToken = refreshToken,
  expiresAtEpochMillis = expiresAtEpochMillis,
)

fun AuthToken.toDataModel(): AuthTokenDataModel = AuthTokenDataModel(
  accessToken = accessToken,
  refreshToken = refreshToken,
  expiresAtEpochMillis = expiresAtEpochMillis,
)
