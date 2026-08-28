package com.sermilion.kmpcomposestarter.core.data.di

import com.sermilion.kmpcomposestarter.common.coroutines.DispatcherProvider
import com.sermilion.kmpcomposestarter.common.di.UserScope
import com.sermilion.kmpcomposestarter.core.data.auth.InMemoryTokenStore
import com.sermilion.kmpcomposestarter.core.domain.auth.TokenStore
import com.sermilion.kmpcomposestarter.core.domain.di.UserScopedCloseable
import com.sermilion.kmpcomposestarter.core.domain.di.UserSessionScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
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

  @Provides
  @SingleIn(UserScope::class)
  fun provideInMemoryTokenStore(): InMemoryTokenStore = InMemoryTokenStore()

  @Provides
  fun provideTokenStore(store: InMemoryTokenStore): TokenStore = store

  @Provides
  @IntoSet
  fun provideTokenStoreCloseable(store: InMemoryTokenStore): UserScopedCloseable = store
}
