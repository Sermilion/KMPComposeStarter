package news.readian.notoesapp.core.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(val isLoggedIn: Boolean = false, val userId: String? = null)
