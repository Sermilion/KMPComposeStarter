package com.sermilion.kmpcomposestarter.core.domain.session

/**
 * Whether the app has decided yet who is signed in.
 *
 * A returning user's session is read from disk before the shell picks a back stack, so the shell
 * must be able to say "not decided yet" instead of guessing. [Loading] means exactly that; it is
 * the initial value and is never returned to. Once the decision is made, the live session is
 * [com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager.userComponentFlow] — this
 * type only reports the launch decision.
 */
sealed interface SessionState {
  data object Loading : SessionState

  data object Authenticated : SessionState

  data object Unauthenticated : SessionState
}
