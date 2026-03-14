package com.sermilion.kmpcomposestarter.core.data.db

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "onboarding_tags")
data class OnboardingTagDataModel(
  @PrimaryKey val id: String,
  val name: String,
  val isSelected: Boolean = false,
)
