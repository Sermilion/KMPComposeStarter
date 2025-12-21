package com.sermilion.kmpcomposestarter.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
  val io: CoroutineDispatcher
  val main: CoroutineDispatcher
  val default: CoroutineDispatcher
}
