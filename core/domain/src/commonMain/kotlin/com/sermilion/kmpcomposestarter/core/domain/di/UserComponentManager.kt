package com.sermilion.kmpcomposestarter.core.domain.di

import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.flow.StateFlow

interface UserComponentManager {
  val userComponent: UserDependencies?
  val userComponentFlow: StateFlow<UserDependencies?>

  /**
   * Returns the live session for [userData]. Creating a session for a different user replaces and
   * tears down the previous one; creating it for the current user is a no-op.
   */
  fun createComponent(userData: UserData): UserDependencies

  fun destroyComponent()
}
