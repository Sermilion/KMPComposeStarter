package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.sermilion.kmpcomposestarter.common.navigation.AuthFlowRoute
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.common.navigation.TopLevelRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.LoginRoute

@Stable
data class StarterNavigationState(
  val isAuthenticated: Boolean = false,
  val authBackStack: SnapshotStateList<AuthFlowRoute> = snapshotStateListOf(LoginRoute),
  val tabBackStacks: Map<TopLevelTab, SnapshotStateList<TopLevelRoute>> =
    createInitialTabBackStacks(),
  val currentTab: TopLevelTab = TopLevelTab.HOME,
) {
  val currentBackStack: SnapshotStateList<out Route>
    get() =
      if (isAuthenticated) {
        tabBackStacks[currentTab] ?: snapshotStateListOf(currentTab.startRoute)
      } else {
        authBackStack
      }

  val currentRoute: Route?
    get() = currentBackStack.lastOrNull()

  /**
   * True when a back press should switch to HOME rather than pop.
   *
   * `NavDisplay` disables its own back handler once there is nothing left to pop, so this branch
   * of the back policy can only live in the host. Deliberately Compose-free so the policy is
   * unit-testable without a composition. At the HOME root — and anywhere on the auth stack — this
   * is false, which is what lets back fall through to the system and close the app.
   */
  val backAtTabRootShouldReturnHome: Boolean
    get() = isAuthenticated && currentTab != TopLevelTab.HOME && currentBackStack.size == 1
}

private fun createInitialTabBackStacks(): Map<TopLevelTab, SnapshotStateList<TopLevelRoute>> =
  TopLevelTab.entries.associateWith { snapshotStateListOf(it.startRoute) }
