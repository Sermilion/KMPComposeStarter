package com.sermilion.kmpcomposestarter.core.data.db

import androidx.room3.Entity
import androidx.room3.PrimaryKey

/** The `users` row. Named for what it is so it cannot be confused with the network model. */
@Entity(tableName = "users")
data class UserEntity(
  @PrimaryKey val id: String,
  val name: String,
  val email: String?,
  val createdAt: Long,
)
