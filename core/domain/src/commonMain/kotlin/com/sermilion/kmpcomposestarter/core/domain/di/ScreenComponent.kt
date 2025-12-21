package com.sermilion.kmpcomposestarter.core.domain.di

import com.sermilion.kmpcomposestarter.common.di.ScreenScope
import com.sermilion.kmpcomposestarter.common.di.UserScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesSubcomponent(ScreenScope::class)
@SingleIn(ScreenScope::class)
interface ScreenComponent {

  val diViewModelFactory: DIViewModelFactory

  @ContributesSubcomponent.Factory(UserScope::class)
  interface Factory {
    fun create(): ScreenComponent
  }
}
