package com.sermilion.kmpcomposestarter.core.ui.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Owner of the per-nav-entry [ViewModelStore]s, scoped to one signed-in session.
 *
 * Navigation 3 keys those stores by content key and hangs them off whichever [ViewModelStoreOwner]
 * the decorator was built with, so an owner that outlives the session — the host owner, say — lets
 * a store keyed `HomeRoute` outlive a logout and hand the next user the previous session's
 * [ScreenComponentHolder] and ViewModels. Pass this owner to
 * `rememberViewModelStoreNavEntryDecorator` instead: it is backed by a host-scoped [ViewModel], so
 * entry state still survives configuration changes, and it is replaced when the session ends.
 */
@Composable
fun rememberNavEntryViewModelStoreOwner(): ViewModelStoreOwner {
  val session by LocalUserComponentManager.current.userComponentFlow.collectAsState()
  val host = rememberNavEntryStoreHost()
  return remember(host, session) { host.ownerFor(session) }
}

/** The host that owns every nav-entry store, kept alive across configuration changes. */
@Composable
private fun rememberNavEntryStoreHost(): NavEntryViewModelStoreHost =
  viewModel { NavEntryViewModelStoreHost() }

internal class NavEntryViewModelStoreHost : ViewModel() {
  private var current: SessionStores? = null

  /**
   * Returns the owner of the nav-entry stores for [session].
   *
   * The current owner is recycled while the session identity is unchanged, and also while signing
   * in: the pre-auth entries hold nothing user-scoped, and clearing them would cancel the very
   * ViewModel coroutine that is completing the sign-in. Leaving a session drops every entry store
   * with it, which is what keeps one user's screen state out of the next user's session.
   */
  fun ownerFor(session: Any?): ViewModelStoreOwner {
    val existing = current
    if (existing != null && existing.canServe(session)) {
      existing.session = session
      return existing
    }
    existing?.viewModelStore?.clear()
    return SessionStores(session).also { current = it }
  }

  override fun onCleared() {
    current?.viewModelStore?.clear()
    current = null
  }

  private class SessionStores(
    var session: Any?,
  ) : ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()

    /** True while this owner already belongs to [candidate], and while it is still pre-auth. */
    fun canServe(candidate: Any?): Boolean = session === candidate || session == null
  }
}
