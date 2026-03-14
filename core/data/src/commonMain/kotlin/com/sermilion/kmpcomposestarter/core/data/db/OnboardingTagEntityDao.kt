package com.sermilion.kmpcomposestarter.core.data.db

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface OnboardingTagEntityDao {
  @Query("SELECT * FROM onboarding_tags ORDER BY name ASC")
  fun observeAll(): Flow<List<OnboardingTagDataModel>>

  @Query("SELECT * FROM onboarding_tags WHERE isSelected = 1 ORDER BY name ASC")
  fun observeSelected(): Flow<List<OnboardingTagDataModel>>

  @Query("SELECT * FROM onboarding_tags ORDER BY name ASC")
  suspend fun getAll(): List<OnboardingTagDataModel>

  @Query("SELECT * FROM onboarding_tags WHERE isSelected = 1 ORDER BY name ASC")
  suspend fun getSelected(): List<OnboardingTagDataModel>

  @Upsert
  suspend fun upsert(tag: OnboardingTagDataModel)

  @Upsert
  suspend fun upsertAll(tags: List<OnboardingTagDataModel>)

  @Query("UPDATE onboarding_tags SET isSelected = :isSelected WHERE id = :id")
  suspend fun updateSelection(id: String, isSelected: Boolean)

  @Query("DELETE FROM onboarding_tags")
  suspend fun deleteAll()
}
