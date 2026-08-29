package com.sermilion.kmpcomposestarter.common.di

import com.sermilion.kmpcomposestarter.common.coroutines.DispatcherProvider
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest

private class SingleDispatcherProvider(
  dispatcher: CoroutineDispatcher,
) : DispatcherProvider {
  override val io: CoroutineDispatcher = dispatcher
  override val main: CoroutineDispatcher = dispatcher
  override val default: CoroutineDispatcher = dispatcher
}

@OptIn(ExperimentalCoroutinesApi::class)
class AppScopeModuleTest :
  FunSpec({

    test("a failure in an app-scoped coroutine reaches the reporter and spares the scope") {
      runTest {
        val reported = mutableListOf<Throwable>()
        val module = object : AppScopeModule {}
        val scope: CoroutineScope =
          module.provideAppCoroutineScope(
            dispatcherProvider = SingleDispatcherProvider(UnconfinedTestDispatcher(testScheduler)),
            reporter = { throwable -> reported += throwable },
          )

        scope.launch { throw IllegalStateException("background work blew up") }

        reported.map { it.message } shouldBe listOf("background work blew up")

        var survived = false
        scope.launch { survived = true }
        survived shouldBe true
      }
    }
  })
