package com.sermilion.kmpcomposestarter.common.di

/**
 * A user-scoped resource that must be released when the session ends. Implementations are
 * multibound into the session component and closed exactly once, by the component manager that
 * swapped the session out.
 *
 * `suspend` because releasing a resource is real work: closing a Room database checkpoints the
 * write-ahead log, and teardown runs from a ViewModel coroutine on the main dispatcher.
 */
fun interface UserScopedCloseable {
  suspend fun close()
}
