package com.sermilion.kmpstarter.core.testing

import com.sermilion.kmpstarter.common.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

class TestDispatcherProvider(testDispatcher: TestDispatcher = StandardTestDispatcher()) : DispatcherProvider {
  override val io: CoroutineDispatcher = testDispatcher
  override val main: CoroutineDispatcher = testDispatcher
  override val default: CoroutineDispatcher = testDispatcher
}
