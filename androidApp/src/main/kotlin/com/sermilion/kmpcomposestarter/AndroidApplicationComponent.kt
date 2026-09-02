package com.sermilion.kmpcomposestarter

import android.app.Application
import com.sermilion.kmpcomposestarter.common.di.StarterViewModelFactory
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.session.SessionRestorer
import com.sermilion.kmpcomposestarter.di.AppComponent
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class AndroidApplicationComponent(
  @get:Provides val application: Application,
) : AppComponent {
  abstract override val userComponentManager: UserComponentManager
  abstract override val viewModelFactory: StarterViewModelFactory
  abstract override val sessionRestorer: SessionRestorer
}

fun createAndroidComponent(application: Application): AndroidApplicationComponent =
  AndroidApplicationComponent::class.create(application)
