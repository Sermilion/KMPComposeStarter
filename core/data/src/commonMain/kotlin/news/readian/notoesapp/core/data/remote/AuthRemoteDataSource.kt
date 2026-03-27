
package news.readian.notoesapp.core.data.remote

import news.readian.notoesapp.core.data.model.AuthResultDataModel
import news.readian.notoesapp.core.data.model.UserDataModel

interface AuthRemoteDataSource {
  suspend fun login(email: String, password: String): AuthResultDataModel
  suspend fun register(email: String, password: String, name: String): AuthResultDataModel
  suspend fun loginGuest(): AuthResultDataModel
  suspend fun logout(token: String)
  suspend fun refreshToken(token: String): AuthResultDataModel
  suspend fun getCurrentUser(token: String): UserDataModel?
}
