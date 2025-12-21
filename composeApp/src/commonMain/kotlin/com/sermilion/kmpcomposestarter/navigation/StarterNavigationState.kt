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

  fun getBackStack(canShowAuthenticated: Boolean): SnapshotStateList<out Route> =
    if (isAuthenticated && canShowAuthenticated) {
      tabBackStacks[currentTab] ?: snapshotStateListOf(currentTab.startRoute)
    } else {
      authBackStack
    }

  val currentBackStack: SnapshotStateList<out Route>
    get() = getBackStack(isAuthenticated)

  val currentRoute: Route?
    get() = currentBackStack.lastOrNull()
}

private fun createInitialTabBackStacks(): Map<TopLevelTab, SnapshotStateList<TopLevelRoute>> =
  TopLevelTab.entries.associateWith { snapshotStateListOf(it.startRoute) }
