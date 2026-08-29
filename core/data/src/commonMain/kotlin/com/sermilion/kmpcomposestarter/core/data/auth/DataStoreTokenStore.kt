package com.sermilion.kmpcomposestarter.core.data.auth

import com.sermilion.kmpcomposestarter.common.di.UserScope
import com.sermilion.kmpcomposestarter.core.data.local.AuthLocalDataSource
import com.sermilion.kmpcomposestarter.core.data.mapper.toDataModel
import com.sermilion.kmpcomposestarter.core.data.mapper.toDomainModel
import com.sermilion.kmpcomposestarter.core.data.model.StoredSession
import com.sermilion.kmpcomposestarter.core.domain.auth.AuthToken
import com.sermilion.kmpcomposestarter.core.domain.auth.TokenStore
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

/**
 * The session's [TokenStore], backed by the same durable store as the session itself.
 *
 * Every read is filtered by [userData]'s id: an instance belonging to a signed-out user reads
 * `null` rather than the token of whoever signed in after them, so a stale reference can never
 * authorize a request as the wrong person.
 */
@Inject
@SingleIn(UserScope::class)
class DataStoreTokenStore(
  private val localDataSource: AuthLocalDataSource,
  private val userData: UserData,
) : TokenStore {

  override suspend fun get(): AuthToken? = localDataSource.getSession()
    ?.takeIf { it.user.id == userData.id }
    ?.token
    ?.toDomainModel()

  override suspend fun save(token: AuthToken) {
    localDataSource.saveSession(
      StoredSession(user = userData.toDataModel(), token = token.toDataModel()),
    )
  }

  override suspend fun clear() {
    localDataSource.clearSession(userData.id)
  }
}
