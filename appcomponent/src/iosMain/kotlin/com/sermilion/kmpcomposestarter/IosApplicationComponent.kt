package com.sermilion.kmpcomposestarter

import com.sermilion.kmpcomposestarter.common.di.PreAuthViewModelFactory
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.session.SessionRestorer
import com.sermilion.kmpcomposestarter.di.AppComponent
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class IosApplicationComponent : AppComponent {
  abstract override val userComponentManager: UserComponentManager
  abstract override val viewModelFactory: PreAuthViewModelFactory
  abstract override val sessionRestorer: SessionRestorer
}

expect fun createIosComponent(): IosApplicationComponent

/** Process-level graph, created once and outside composition. */
val iosAppComponent: IosApplicationComponent by lazy { createIosComponent() }
