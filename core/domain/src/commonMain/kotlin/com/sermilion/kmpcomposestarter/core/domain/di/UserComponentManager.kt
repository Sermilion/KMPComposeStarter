package com.sermilion.kmpcomposestarter.core.domain.di

import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.flow.StateFlow

interface UserComponentManager {
  val userComponent: UserDependencies?
  val userComponentFlow: StateFlow<UserDependencies?>

  /**
   * Returns the live session for [userData]. Creating a session for a different user replaces the
   * previous one and releases it; creating it for the current user is a no-op.
   *
   * `suspend` because releasing the replaced session closes its database, which is file I/O and
   * must not run on the caller's dispatcher when that caller is a ViewModel on the main thread.
   */
  suspend fun createComponent(userData: UserData): UserDependencies

  suspend fun destroyComponent()
}
