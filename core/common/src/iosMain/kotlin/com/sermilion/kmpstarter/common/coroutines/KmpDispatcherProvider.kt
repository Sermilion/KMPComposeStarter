package com.sermilion.kmpstarter.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * iOS-specific implementation of [DispatcherProvider].
 *
 * This is the ONLY place where direct [Dispatchers] usage is acceptable,
 * as it provides the abstraction layer for the rest of the application.
 * All other code should inject [DispatcherProvider] instead.
 *
 * For iOS (Kotlin Native):
 * - [io]: Uses [Dispatchers.Default] because Kotlin Native doesn't have a separate IO dispatcher.
 *   The Default dispatcher is backed by a thread pool suitable for both compute and IO operations.
 * - [main]: Uses [Dispatchers.Main] for main thread operations
 * - [default]: Uses [Dispatchers.Default] for CPU-intensive work
 */
actual class KmpDispatcherProvider : DispatcherProvider {
  actual override val io: CoroutineDispatcher = Dispatchers.Default
  actual override val main: CoroutineDispatcher = Dispatchers.Main
  actual override val default: CoroutineDispatcher = Dispatchers.Default
}
