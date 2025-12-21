package com.sermilion.kmpstarter.core.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(val isLoggedIn: Boolean = false, val userId: String? = null)
