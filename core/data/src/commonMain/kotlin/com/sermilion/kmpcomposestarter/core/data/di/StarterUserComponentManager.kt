package com.sermilion.kmpcomposestarter.core.data.di

import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.core.domain.di.UserDependencies
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

/**
 * Owns the signed-in session. The [MutableStateFlow] is the only source of truth, and every
 * transition runs under [transitionLock], so sign-in and sign-out are ordered by lock acquisition:
 * whoever goes last wins, a sign-out can never be overtaken by a sign-in that started before it,
 * and the session handed back to a caller is not being torn down as it is returned. Publication
 * still goes through a compare-and-set, which under the lock also asserts that nothing mutates the
 * session from outside — and which keeps teardown exactly-once.
 */
@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class StarterUserComponentManager(
  private val userComponentFactory: UserComponent.Factory,
) : UserComponentManager {
  private val transitionLock = SynchronizedObject()

  private val state = MutableStateFlow<UserDependencies?>(null)

  override val userComponent: UserDependencies?
    get() = state.value

  override val userComponentFlow: StateFlow<UserDependencies?> = state.asStateFlow()

  override fun createComponent(userData: UserData): UserDependencies =
    synchronized(transitionLock) {
      val current = state.value
      if (current != null && current.userData == userData) {
        current
      } else {
        val replacement = userComponentFactory.create(userData)
        check(state.compareAndSet(current, replacement)) {
          "Session changed outside the transition lock; every transition must hold it."
        }
        current?.let(::tearDown)
        replacement
      }
    }

  override fun destroyComponent() {
    synchronized(transitionLock) {
      state.getAndUpdate { null }?.let(::tearDown)
    }
  }

  private fun tearDown(dependencies: UserDependencies) {
    dependencies.userSessionScope.cancel()
    dependencies.userScopedCloseables.forEach { it.close() }
  }
}
