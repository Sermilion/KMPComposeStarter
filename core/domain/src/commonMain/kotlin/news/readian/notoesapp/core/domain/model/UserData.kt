package news.readian.notoesapp.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(val id: String, val email: String, val name: String, val token: String)
