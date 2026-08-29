package com.sermilion.kmpcomposestarter.core.data.repository

import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.core.data.mapper.toDomainModel
import com.sermilion.kmpcomposestarter.core.data.model.AuthResultDataModel
import com.sermilion.kmpcomposestarter.core.data.remote.AuthRemoteDataSource
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.di.UserDependencies
import com.sermilion.kmpcomposestarter.core.domain.model.LoginResult
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.core.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.coroutines.cancellation.CancellationException

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class StarterAuthRepository(
  private val remoteDataSource: AuthRemoteDataSource,
  private val userComponentManager: UserComponentManager,
  externalScope: CoroutineScope,
) : AuthRepository {

  override val isLoggedIn: StateFlow<Boolean> = userComponentManager.userComponentFlow
    .map { it != null }
    .stateIn(
      scope = externalScope,
      started = SharingStarted.Eagerly,
      initialValue = userComponentManager.userComponent != null,
    )

  override val currentUser: UserData?
    get() = userComponentManager.userComponent?.userData

  override suspend fun login(email: String, password: String): LoginResult =
    startSession(remoteDataSource.login(email, password))

  override suspend fun register(email: String, password: String, name: String): LoginResult =
    startSession(remoteDataSource.register(email, password, name))

  /**
   * Signs out remotely, but tears the local session down whether or not that call succeeds: a
   * failed or cancelled network sign-out must never leave an authenticated session alive. The
   * network failure still surfaces, after teardown.
   */
  override suspend fun logout() {
    val session = userComponentManager.userComponent
    try {
      remoteDataSource.logout()
    } finally {
      try {
        eraseStoredSession(session)
      } finally {
        userComponentManager.destroyComponent()
      }
    }
  }

  /**
   * Opens the session before anything is written into it, so the token and the user row land in
   * the new user's store and database rather than the outgoing user's.
   */
  private suspend fun startSession(result: AuthResultDataModel): LoginResult = when (result) {
    is AuthResultDataModel.Success -> {
      val userData = result.user.toDomainModel()
      val session = userComponentManager.createComponent(userData)
      session.tokenStore.save(result.token.toDomainModel())
      session.userRepository.saveUser(userData)
      LoginResult.Success(userData)
    }
    is AuthResultDataModel.Failure -> LoginResult.Failure(result.error)
  }

  private suspend fun eraseStoredSession(session: UserDependencies?) {
    try {
      session?.tokenStore?.clear()
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      // Reported rather than rethrown: teardown still has to finish, and a sign-out that leaves
      // the session alive is worse than one that leaves a file behind.
      Logger.e(TAG, e) { "Could not erase the stored session on sign-out." }
    }
  }

  private companion object {
    const val TAG = "StarterAuthRepository"
  }
}
