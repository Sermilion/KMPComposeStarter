package com.sermilion.kmpcomposestarter.core.data.auth

import com.sermilion.kmpcomposestarter.core.domain.auth.AuthToken
import com.sermilion.kmpcomposestarter.core.domain.auth.TokenStore
import com.sermilion.kmpcomposestarter.core.domain.di.UserScopedCloseable
import kotlinx.atomicfu.atomic

/**
 * Default [TokenStore] binding: keeps the session's token in memory only, so it disappears with
 * the process and with the user component. Subtask 3 replaces this with DataStore-backed storage.
 *
 * shortcut: in-memory only, replace once sessions must survive process death
 */
class InMemoryTokenStore : TokenStore, UserScopedCloseable {

  private val current = atomic<AuthToken?>(null)

  override suspend fun get(): AuthToken? = current.value

  override suspend fun save(token: AuthToken) {
    current.value = token
  }

  override suspend fun clear() {
    current.value = null
  }

  override fun close() {
    current.value = null
  }
}
