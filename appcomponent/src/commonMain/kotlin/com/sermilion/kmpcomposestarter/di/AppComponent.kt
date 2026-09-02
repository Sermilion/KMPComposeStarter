package com.sermilion.kmpcomposestarter.di

import com.sermilion.kmpcomposestarter.common.di.PreAuthViewModelFactory
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.session.SessionRestorer

/**
 * What the UI needs from the DI graph, independent of which platform built it.
 *
 * Every platform creates its graph once per process and hands it to `StarterRoot`, so no host
 * duplicates the wiring and no graph is ever built inside composition.
 */
interface AppComponent {
  val userComponentManager: UserComponentManager
  val viewModelFactory: PreAuthViewModelFactory
  val sessionRestorer: SessionRestorer
}
