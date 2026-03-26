package news.readian.notoesapp.common.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Provides
import news.readian.notoesapp.common.coroutines.DispatcherProvider
import news.readian.notoesapp.common.coroutines.KmpDispatcherProvider
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
  fun provideAppCoroutineScope(dispatcherProvider: DispatcherProvider): CoroutineScope =
    CoroutineScope(SupervisorJob() + dispatcherProvider.default)
}
