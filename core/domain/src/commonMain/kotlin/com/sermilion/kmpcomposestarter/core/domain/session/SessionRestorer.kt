package com.sermilion.kmpcomposestarter.core.domain.session

import kotlinx.coroutines.flow.StateFlow

/**
 * Rebuilds a stored session at launch, before the shell chooses a back stack.
 *
 * The contract is here, next to [SessionState], so the app shell's `AppComponent` names an
 * abstraction rather than a `core:data` class. A fork that restores differently - refreshing the
 * token on launch, say - binds its own implementation and touches no platform component.
 */
interface SessionRestorer {
  /**
   * Starts at [SessionState.Loading] and moves exactly once, so a returning user never sees
   * [SessionState.Unauthenticated] on the way to their session.
   */
  val state: StateFlow<SessionState>

  /** Idempotent: the decision is made once per process, however often the shell recomposes. */
  suspend fun restore()
}
