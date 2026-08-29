package com.sermilion.kmpcomposestarter.di

import com.sermilion.kmpcomposestarter.common.di.StarterViewModelFactory
import com.sermilion.kmpcomposestarter.core.data.session.SessionRestorer
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager

/**
 * What the UI needs from the DI graph, independent of which platform built it.
 *
 * Every platform creates its graph once per process and hands it to `StarterRoot`, so no host
 * duplicates the wiring and no graph is ever built inside composition.
 */
interface AppComponent {
  val userComponentManager: UserComponentManager
  val viewModelFactory: StarterViewModelFactory
  val sessionRestorer: SessionRestorer
}
