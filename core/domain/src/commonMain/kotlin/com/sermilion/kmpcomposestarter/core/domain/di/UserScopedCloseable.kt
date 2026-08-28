package com.sermilion.kmpcomposestarter.core.domain.di

/**
 * A user-scoped resource that must be released when the session ends. Implementations are
 * multibound into [UserDependencies.userScopedCloseables] and closed exactly once, by the
 * component manager that swapped the session out.
 */
fun interface UserScopedCloseable {
  fun close()
}
