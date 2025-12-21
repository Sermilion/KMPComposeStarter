package com.sermilion.kmpcomposestarter.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher

expect class KmpDispatcherProvider() : DispatcherProvider {
  override val io: CoroutineDispatcher
  override val main: CoroutineDispatcher
  override val default: CoroutineDispatcher
}
