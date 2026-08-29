package com.sermilion.kmpcomposestarter.core.data.network

import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import io.ktor.client.HttpClient
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface HttpClientModule {
  /**
   * One client per process. It outlives any single session, so it reads the bearer token through
   * the component manager on every request instead of capturing a `TokenStore`: capturing one
   * would keep authorizing requests as the user who signed out.
   *
   * Desktop has no process lifecycle owner, so `Main.kt` closes this client on shutdown.
   */
  @Provides
  @SingleIn(AppScope::class)
  fun provideHttpClient(userComponentManager: UserComponentManager): HttpClient =
    createPlatformHttpClient {
      configureStarterHttpClient(
        loadToken = { userComponentManager.userComponent?.tokenStore?.get() },
        ktorLogger = KermitKtorLogger,
      )
    }
}
