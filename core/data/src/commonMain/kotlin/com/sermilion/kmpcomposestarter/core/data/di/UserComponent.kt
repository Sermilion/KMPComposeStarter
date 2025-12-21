package com.sermilion.kmpcomposestarter.core.data.di

import com.sermilion.kmpcomposestarter.common.di.UserScope
import com.sermilion.kmpcomposestarter.core.domain.di.ScreenComponent
import com.sermilion.kmpcomposestarter.core.domain.di.UserDependencies
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesSubcomponent(UserScope::class)
@SingleIn(UserScope::class)
interface UserComponent : UserDependencies {

  override val userData: UserData
  override val screenComponentFactory: ScreenComponent.Factory

  @ContributesSubcomponent.Factory(AppScope::class)
  interface Factory {
    fun create(userData: UserData): UserComponent
  }
}
