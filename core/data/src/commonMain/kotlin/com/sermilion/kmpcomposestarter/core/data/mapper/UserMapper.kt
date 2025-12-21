package com.sermilion.kmpcomposestarter.core.data.mapper

import com.sermilion.kmpcomposestarter.core.data.model.UserLocalDataModel
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.datetime.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun UserData.toLocalDataModel(): UserLocalDataModel = UserLocalDataModel(
  id = Uuid.parse(id),
  name = name,
  email = email,
  createdAt = Clock.System.now(),
)

@OptIn(ExperimentalUuidApi::class)
fun UserLocalDataModel.toDomainModel(token: String = ""): UserData = UserData(
  id = id.toString(),
  email = email.orEmpty(),
  name = name,
  token = token,
)

@OptIn(ExperimentalUuidApi::class)
fun List<UserLocalDataModel>.toDomainModels(): List<UserData> = map { it.toDomainModel() }
