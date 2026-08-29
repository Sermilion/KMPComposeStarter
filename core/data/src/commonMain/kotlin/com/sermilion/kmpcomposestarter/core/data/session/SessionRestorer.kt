package com.sermilion.kmpcomposestarter.core.data.session

import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.core.data.local.AuthLocalDataSource
import com.sermilion.kmpcomposestarter.core.data.mapper.toDomainModel
import com.sermilion.kmpcomposestarter.core.data.model.StoredSession
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.session.SessionState
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.coroutines.cancellation.CancellationException

/**
 * Rebuilds a stored session at launch, before the shell chooses a back stack.
 *
 * [state] starts at [SessionState.Loading] and moves exactly once. A returning user therefore
 * never sees [SessionState.Unauthenticated] on the way to their session — which is the login
 * flash this exists to remove.
 */
@Inject
@SingleIn(AppScope::class)
class SessionRestorer(
  private val localDataSource: AuthLocalDataSource,
  private val userComponentManager: UserComponentManager,
) {

  private val started = atomic(false)

  private val mutableState = MutableStateFlow<SessionState>(SessionState.Loading)

  val state: StateFlow<SessionState> = mutableState.asStateFlow()

  /** Idempotent: the decision is made once per process, however often the shell recomposes. */
  suspend fun restore() {
    if (!started.compareAndSet(expect = false, update = true)) return

    val session = readStoredSession()
    mutableState.value = if (session == null) {
      SessionState.Unauthenticated
    } else {
      // Publishing Authenticated only after the component exists means the first composition
      // that observes it already has a session to render.
      userComponentManager.createComponent(session.user.toDomainModel())
      SessionState.Authenticated
    }
  }

  private suspend fun readStoredSession(): StoredSession? = try {
    localDataSource.getSession()
  } catch (e: CancellationException) {
    started.value = false
    throw e
  } catch (e: Exception) {
    Logger.e(TAG, e) { "Could not read the stored session; starting signed out." }
    null
  }

  private companion object {
    const val TAG = "SessionRestorer"
  }
}
