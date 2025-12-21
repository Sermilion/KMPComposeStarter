package com.sermilion.kmpcomposestarter.core.domain.di

import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.flow.StateFlow

interface UserComponentManager {
  val userComponent: UserDependencies?
  val userComponentFlow: StateFlow<UserDependencies?>

  fun createComponent(userData: UserData)
  fun destroyComponent()
}
