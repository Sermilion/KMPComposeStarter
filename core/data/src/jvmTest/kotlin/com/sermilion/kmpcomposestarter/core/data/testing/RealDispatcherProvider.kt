package com.sermilion.kmpcomposestarter.core.data.testing

import com.sermilion.kmpcomposestarter.common.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Real dispatchers, for the tests that drive a real SQLite file.
 *
 * A `TestDispatcher` would put the database's own blocking work on the test's virtual clock,
 * which is a good way to hang rather than a good way to test.
 */
object RealDispatcherProvider : DispatcherProvider {
  override val io: CoroutineDispatcher = Dispatchers.IO
  override val main: CoroutineDispatcher = Dispatchers.Default
  override val default: CoroutineDispatcher = Dispatchers.Default
}
