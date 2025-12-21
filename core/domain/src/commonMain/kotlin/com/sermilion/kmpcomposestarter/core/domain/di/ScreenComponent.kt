package com.sermilion.kmpcomposestarter.core.domain.di

import com.sermilion.kmpcomposestarter.common.di.UserFeatureScope
import com.sermilion.kmpcomposestarter.common.di.UserScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent

@ContributesSubcomponent(UserFeatureScope::class)
interface ScreenComponent {

  val diViewModelFactory: DIViewModelFactory

  @ContributesSubcomponent.Factory(UserScope::class)
  interface Factory {
    fun create(): ScreenComponent
  }
}
