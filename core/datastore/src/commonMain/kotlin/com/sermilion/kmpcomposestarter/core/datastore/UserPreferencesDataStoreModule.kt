package com.sermilion.kmpcomposestarter.core.datastore

import androidx.datastore.core.DataStore
import com.sermilion.kmpcomposestarter.common.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface UserPreferencesDataStoreModule {

  /**
   * The store lives as long as the process. Its scope is deliberately not the app scope: DataStore
   * serializes every read and write through it, and that work belongs on the IO dispatcher.
   */
  @Provides
  @SingleIn(AppScope::class)
  fun provideUserPreferencesDataStore(
    pathProvider: UserPreferencesPathProvider,
    dispatcherProvider: DispatcherProvider,
  ): DataStore<UserPreferences> = createUserPreferencesDataStore(
    scope = CoroutineScope(SupervisorJob() + dispatcherProvider.io),
    producePath = pathProvider::userPreferencesPath,
  )
}
