package com.sermilion.kmpcomposestarter

import com.sermilion.kmpcomposestarter.common.di.PreAuthViewModelFactory
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.session.SessionRestorer
import com.sermilion.kmpcomposestarter.di.AppComponent
import io.ktor.client.HttpClient
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class JvmApplicationComponent : AppComponent {
  abstract override val userComponentManager: UserComponentManager
  abstract override val viewModelFactory: PreAuthViewModelFactory
  abstract override val sessionRestorer: SessionRestorer

  /**
   * Desktop has no framework-owned process lifecycle, so `main` closes the client itself.
   * Exposed here rather than reached through a global so the shutdown hook cannot resurrect
   * a graph that was never built.
   */
  abstract val httpClient: HttpClient
}

/** Process-level graph, created once and outside composition. */
object JvmAppComponentHolder {
  val component: JvmApplicationComponent by lazy { JvmApplicationComponent::class.create() }
}
