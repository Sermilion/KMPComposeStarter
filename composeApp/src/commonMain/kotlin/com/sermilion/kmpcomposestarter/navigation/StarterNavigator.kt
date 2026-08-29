package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.MutableState
import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.common.navigation.AuthFlowRoute
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.common.navigation.TopLevelRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.LoginRoute

/**
 * The only writer of [StarterNavigationState].
 *
 * @param onRejectedNavigation invoked when [navigate] is handed a route that does not belong on
 *   the current stack. The production default logs at ERROR; it is injectable so a test can
 *   observe a rejection without scraping logs.
 */
class StarterNavigator(
  private val state: MutableState<StarterNavigationState>,
  private val onRejectedNavigation: (Route, StarterNavigationState) -> Unit =
    ::logRejectedNavigation,
) {
  /**
   * Pushes [route] onto the stack it belongs to and returns whether it was accepted.
   *
   * Three routes are rejected rather than silently dropped, because each is a wiring mistake that
   * would otherwise surface as a corrupted screen much later:
   * - a tab root, which would collide with the owning tab's own entry on content key,
   * - an [AuthFlowRoute] while signed in, which renders a login form inside the signed-in shell,
   * - a [TopLevelRoute] while signed out, whose screen component does not exist yet.
   *
   * A rejection leaves the state untouched.
   */
  fun navigate(route: Route): Boolean {
    val currentState = state.value
    return when {
      route.isTabRoot -> reject(route, currentState)

      !currentState.isAuthenticated && route is AuthFlowRoute -> {
        val updatedStack = currentState.authBackStack.toMutableList()
        updatedStack.add(route)
        state.value = currentState.copy(authBackStack = updatedStack.toSnapshotStateList())
        true
      }

      currentState.isAuthenticated && route is TopLevelRoute -> {
        val currentTab = currentState.currentTab
        val currentStack =
          currentState.tabBackStacks[currentTab]?.toMutableList() ?: mutableListOf()
        currentStack.add(route)
        val updatedTabStacks = currentState.tabBackStacks.toMutableMap()
        updatedTabStacks[currentTab] = currentStack.toSnapshotStateList()
        state.value = currentState.copy(tabBackStacks = updatedTabStacks)
        true
      }

      else -> reject(route, currentState)
    }
  }

  private fun reject(
    route: Route,
    currentState: StarterNavigationState,
  ): Boolean {
    onRejectedNavigation(route, currentState)
    return false
  }

  fun navigateToTopLevel(tab: TopLevelTab) {
    val currentState = state.value
    if (currentState.currentTab == tab) {
      val currentStack = currentState.tabBackStacks[tab]
      if (currentStack != null && currentStack.size > 1) {
        val updatedTabStacks = currentState.tabBackStacks.toMutableMap()
        updatedTabStacks[tab] = listOf(tab.startRoute).toSnapshotStateList()
        state.value = currentState.copy(tabBackStacks = updatedTabStacks)
      }
    } else {
      state.value = currentState.copy(currentTab = tab)
    }
  }

  fun goBack(): Boolean {
    val currentState = state.value
    return if (currentState.isAuthenticated) {
      goBackInTab(currentState)
    } else {
      goBackInAuth(currentState)
    }
  }

  private fun goBackInTab(currentState: StarterNavigationState): Boolean {
    val currentTab = currentState.currentTab
    val currentStack = currentState.tabBackStacks[currentTab] ?: return false

    return if (currentStack.size > 1) {
      val updatedStack = currentStack.toMutableList()
      updatedStack.removeLastOrNull()
      val updatedTabStacks = currentState.tabBackStacks.toMutableMap()
      updatedTabStacks[currentTab] = updatedStack.toSnapshotStateList()
      state.value = currentState.copy(tabBackStacks = updatedTabStacks)
      true
    } else {
      false
    }
  }

  private fun goBackInAuth(currentState: StarterNavigationState): Boolean =
    if (currentState.authBackStack.size > 1) {
      val updatedStack = currentState.authBackStack.toMutableList()
      updatedStack.removeLastOrNull()
      state.value = currentState.copy(authBackStack = updatedStack.toSnapshotStateList())
      true
    } else {
      false
    }

  /**
   * Applies a session transition.
   *
   * Both directions rebuild every tab stack and reset the current tab: whatever the previous user
   * pushed must not be where the next user lands, and a signed-out state must not keep a
   * signed-in tab alive behind the login screen.
   */
  fun onLoginStateChanged(isLoggedIn: Boolean) {
    val currentState = state.value
    val freshTabStacks =
      TopLevelTab.entries.associateWith {
        listOf(it.startRoute).toSnapshotStateList()
      }
    state.value =
      if (isLoggedIn) {
        currentState.copy(
          isAuthenticated = true,
          tabBackStacks = freshTabStacks,
          currentTab = TopLevelTab.HOME,
        )
      } else {
        currentState.copy(
          isAuthenticated = false,
          authBackStack = listOf<AuthFlowRoute>(LoginRoute).toSnapshotStateList(),
          tabBackStacks = freshTabStacks,
          currentTab = TopLevelTab.HOME,
        )
      }
  }
}

/**
 * The production rejection handler.
 *
 * ERROR-level only: there is no cross-platform debug flag in this template to gate a hard failure
 * on, and crashing a release build over a mis-wired navigation call is worse than the log. Tests
 * assert on [StarterNavigator.navigate]'s return value and on an injected handler instead.
 */
private fun logRejectedNavigation(
  route: Route,
  state: StarterNavigationState,
) {
  Logger.e("StarterNavigator") {
    "Rejected navigate(${route::class.simpleName}): isAuthenticated=${state.isAuthenticated}, " +
      "currentTab=${state.currentTab}. Tab roots move through navigateToTopLevel(); auth-flow " +
      "and top-level routes never share a stack."
  }
}
