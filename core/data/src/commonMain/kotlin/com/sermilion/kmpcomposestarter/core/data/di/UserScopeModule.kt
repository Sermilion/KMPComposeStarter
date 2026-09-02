package com.sermilion.kmpcomposestarter.core.data.di

import com.sermilion.kmpcomposestarter.common.coroutines.DispatcherProvider
import com.sermilion.kmpcomposestarter.common.di.UserScope
import com.sermilion.kmpcomposestarter.common.di.UserScopedCloseable
import com.sermilion.kmpcomposestarter.core.data.auth.DataStoreTokenStore
import com.sermilion.kmpcomposestarter.core.data.db.DatabaseProvider
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import com.sermilion.kmpcomposestarter.core.data.db.dao.StarterUserDao
import com.sermilion.kmpcomposestarter.core.data.db.dao.UserDao
import com.sermilion.kmpcomposestarter.core.data.repository.StarterUserRepository
import com.sermilion.kmpcomposestarter.core.domain.auth.TokenStore
import com.sermilion.kmpcomposestarter.core.domain.di.UserSessionScope
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.core.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(UserScope::class)
interface UserScopeModule {
  @Provides
  @SingleIn(UserScope::class)
  fun provideUserSessionScope(dispatcherProvider: DispatcherProvider): UserSessionScope =
    UserSessionScope(CoroutineScope(SupervisorJob() + dispatcherProvider.default))

  /**
   * This session's database, keyed by the signed-in user's id at the moment the session is built.
   * Nothing here can reach a captured or stale user.
   */
  @Provides
  @SingleIn(UserScope::class)
  fun provideUserDatabase(
    databaseProvider: DatabaseProvider,
    userData: UserData,
  ): UserDatabase = databaseProvider.provideUserDatabase(userData.id)

  @Provides
  fun provideUserDao(dao: StarterUserDao): UserDao = dao

  @Provides
  fun provideUserRepository(repository: StarterUserRepository): UserRepository = repository

  @Provides
  fun provideTokenStore(store: DataStoreTokenStore): TokenStore = store

  /**
   * Releases the session's hold on the database file at sign-out, so the cached instance cannot
   * be handed to whoever signs in next and the next sign-in opens a fresh one.
   */
  @Provides
  @IntoSet
  fun provideUserDatabaseCloseable(
    databaseProvider: DatabaseProvider,
    userData: UserData,
    dispatcherProvider: DispatcherProvider,
  ): UserScopedCloseable =
    UserScopedCloseable {
      withContext(dispatcherProvider.io) { databaseProvider.closeDatabaseForUser(userData.id) }
    }
}
