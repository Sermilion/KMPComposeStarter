package com.sermilion.kmpcomposestarter

import com.sermilion.kmpcomposestarter.common.di.StarterViewModelFactory
import com.sermilion.kmpcomposestarter.core.data.di.UserComponent
import com.sermilion.kmpcomposestarter.core.data.session.SessionRestorer
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.repository.AuthRepository
import com.sermilion.kmpcomposestarter.di.AppComponent
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class IosApplicationComponent : AppComponent {
  abstract override val userComponentManager: UserComponentManager
  abstract override val viewModelFactory: StarterViewModelFactory
  abstract override val sessionRestorer: SessionRestorer

  abstract val userComponentFactory: UserComponent.Factory
  abstract val authRepository: AuthRepository
}

expect fun createIosComponent(): IosApplicationComponent

/** Process-level graph, created once and outside composition. */
val iosAppComponent: IosApplicationComponent by lazy { createIosComponent() }
