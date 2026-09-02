package com.sermilion.kmpcomposestarter.common.di

/** Receives failures that escape an app-scoped coroutine instead of reaching the platform. */
fun interface UnhandledErrorReporter {
  fun report(throwable: Throwable)
}
