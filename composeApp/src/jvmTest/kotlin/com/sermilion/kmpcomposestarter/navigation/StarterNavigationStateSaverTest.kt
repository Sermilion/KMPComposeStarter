package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.sermilion.kmpcomposestarter.common.navigation.AuthFlowRoute
import com.sermilion.kmpcomposestarter.common.navigation.TopLevelRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.LoginRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.RegisterRoute
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailRoute
import com.sermilion.kmpcomposestarter.feature.home.navigation.HomeRoute
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class StarterNavigationStateSaverTest :
  FunSpec({

    test("a signed-in state survives save and restore with every field intact") {
      val state =
        StarterNavigationState(
          isAuthenticated = true,
          authBackStack =
            SnapshotStateList<AuthFlowRoute>().apply {
              add(LoginRoute)
              add(RegisterRoute)
            },
          tabBackStacks =
            TopLevelTab.entries.associateWith { tab ->
              SnapshotStateList<TopLevelRoute>().apply {
                add(tab.startRoute)
                if (tab == TopLevelTab.HOME) add(DetailRoute("item-1"))
              }
            },
          currentTab = TopLevelTab.SETTINGS,
        )

      val restored = decodeNavigationState(encodeNavigationState(state))

      // Rotating on a pushed screen must not drop the user back to the tab root, and a signed-in
      // user must not come back as signed out looking at the login screen.
      restored.isAuthenticated shouldBe true
      restored.currentTab shouldBe TopLevelTab.SETTINGS
      restored.authBackStack.toList() shouldBe listOf(LoginRoute, RegisterRoute)
      restored.tabBackStacks.getValue(TopLevelTab.HOME).toList() shouldBe
        listOf(HomeRoute, DetailRoute("item-1"))
      TopLevelTab.entries.forEach { tab ->
        restored.tabBackStacks.getValue(tab).first() shouldBe tab.startRoute
      }
    }

    test("a payload this build cannot decode restores as null rather than throwing") {
      // A route that changes shape in an update leaves existing installs holding a payload the
      // new build cannot read. Throwing out of restore would make that an unclearable crash loop
      // on cold start; null hands rememberSaveable a fresh state instead.
      StarterNavigationStateSaver.restore("{}") shouldBe null
    }

    test("a signed-out state round-trips as signed out") {
      val restored = decodeNavigationState(encodeNavigationState(StarterNavigationState()))

      restored.isAuthenticated shouldBe false
      restored.currentBackStack.toList() shouldBe listOf(LoginRoute)
    }
  })
