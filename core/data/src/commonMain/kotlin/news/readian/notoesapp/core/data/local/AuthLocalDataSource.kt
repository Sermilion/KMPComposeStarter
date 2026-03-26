package news.readian.notoesapp.core.data.local

import kotlinx.coroutines.flow.Flow
import news.readian.notoesapp.core.data.model.AuthTokenDataModel
import news.readian.notoesapp.core.data.model.UserDataModel

interface AuthLocalDataSource {
  fun observeCurrentUser(): Flow<UserDataModel?>
  suspend fun getCurrentUser(): UserDataModel?
  suspend fun getAuthToken(): AuthTokenDataModel?
  suspend fun saveUser(user: UserDataModel)
  suspend fun saveAuthToken(token: AuthTokenDataModel)
  suspend fun clearUserData()
}
