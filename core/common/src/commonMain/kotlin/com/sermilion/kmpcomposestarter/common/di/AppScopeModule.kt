package com.sermilion.kmpcomposestarter.common.di

import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.common.coroutines.DispatcherProvider
import com.sermilion.kmpcomposestarter.common.coroutines.KmpDispatcherProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface AppScopeModule {
  @Provides
  @SingleIn(AppScope::class)
  fun provideDispatcherProvider(): DispatcherProvider = KmpDispatcherProvider()

  @Provides
  @SingleIn(AppScope::class)
  fun provideUnhandledErrorReporter(): UnhandledErrorReporter =
    UnhandledErrorReporter { throwable ->
      Logger.e("AppCoroutineScope", throwable) { "Unhandled exception in app-scoped coroutine" }
    }

  @Provides
  @SingleIn(AppScope::class)
  fun provideAppCoroutineScope(
    dispatcherProvider: DispatcherProvider,
    reporter: UnhandledErrorReporter,
  ): CoroutineScope =
    CoroutineScope(
      SupervisorJob() +
        dispatcherProvider.default +
        CoroutineExceptionHandler { _, throwable -> reporter.report(throwable) },
    )
}
