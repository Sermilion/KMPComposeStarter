
package news.readian.notoesapp.navigation

import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshots.SnapshotStateList
import news.readian.notoesapp.common.navigation.AuthFlowRoute
import news.readian.notoesapp.common.navigation.Route
import news.readian.notoesapp.common.navigation.TopLevelRoute
import news.readian.notoesapp.feature.onboarding.navigation.TutorialRoute

@Stable
data class StarterNavigationState(
  val isAuthenticated: Boolean = false,
  val authBackStack: SnapshotStateList<AuthFlowRoute> = snapshotStateListOf(TutorialRoute),
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
