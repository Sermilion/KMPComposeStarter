package com.sermilion.kmpcomposestarter.core.data.session

import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.core.data.local.AuthLocalDataSource
import com.sermilion.kmpcomposestarter.core.data.mapper.toDomainModel
import com.sermilion.kmpcomposestarter.core.data.model.StoredSession
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.session.SessionRestorer
import com.sermilion.kmpcomposestarter.core.domain.session.SessionState
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.coroutines.cancellation.CancellationException

/**
 * The durable-store implementation of [SessionRestorer].
 *
 * When a session is on disk it opens that user's component *before* publishing
 * [SessionState.Authenticated], so the first composition that observes the decision already has a
 * session to render. That ordering is what removes the login flash a returning user used to see.
 */
@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class StarterSessionRestorer(
  private val localDataSource: AuthLocalDataSource,
  private val userComponentManager: UserComponentManager,
) : SessionRestorer {
  private val started = atomic(false)

  private val mutableState = MutableStateFlow<SessionState>(SessionState.Loading)

  override val state: StateFlow<SessionState> = mutableState.asStateFlow()

  override suspend fun restore() {
    if (!started.compareAndSet(expect = false, update = true)) return

    val session = readStoredSession()
    mutableState.value =
      if (session == null) {
        SessionState.Unauthenticated
      } else {
        userComponentManager.createComponent(session.user.toDomainModel())
        SessionState.Authenticated
      }
  }

  private suspend fun readStoredSession(): StoredSession? =
    try {
      localDataSource.getSession()
    } catch (e: CancellationException) {
      started.value = false
      throw e
    } catch (e: Exception) {
      Logger.e(TAG, e) { "Could not read the stored session; starting signed out." }
      null
    }

  private companion object {
    const val TAG = "StarterSessionRestorer"
  }
}
