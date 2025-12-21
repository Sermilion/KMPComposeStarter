package com.sermilion.kmpcomposestarter

import com.sermilion.kmpcomposestarter.common.di.SingleIn
import com.sermilion.kmpcomposestarter.common.di.ViewModelFactory
import com.sermilion.kmpcomposestarter.common.di.ViewModelProvider
import com.sermilion.kmpcomposestarter.core.data.di.UserComponent
import com.sermilion.kmpcomposestarter.core.data.repository.AuthRepository
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class IosApplicationComponent : ViewModelProvider {

  abstract val userComponentFactory: UserComponent.Factory
  abstract val userComponentManager: UserComponentManager
  abstract val authRepository: AuthRepository

  val viewModelFactory: ViewModelFactory
    get() = viewModelFactoryImpl

  abstract val viewModelFactoryImpl: com.sermilion.kmpcomposestarter.common.di.StarterViewModelFactory
}

expect fun createIosComponent(): IosApplicationComponent
