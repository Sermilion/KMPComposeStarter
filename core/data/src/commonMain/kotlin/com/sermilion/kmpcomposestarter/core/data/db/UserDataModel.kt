package com.sermilion.kmpcomposestarter.core.data.db

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "users")
data class UserDataModel(
  @PrimaryKey val id: String,
  val name: String,
  val email: String?,
  val createdAt: Long,
)
