package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.sermilion.kmpcomposestarter.common.navigation.AuthFlowRoute
import com.sermilion.kmpcomposestarter.common.navigation.TopLevelRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.LoginRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.RegisterRoute
import com.sermilion.kmpcomposestarter.feature.home.navigation.HomeRoute
import com.sermilion.kmpcomposestarter.feature.profile.navigation.ProfileRoute
import com.sermilion.kmpcomposestarter.feature.settings.navigation.SettingsRoute
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class StarterNavigationStateTest :
  FunSpec({

    test("default state is unauthenticated with LoginRoute") {
      val state = StarterNavigationState()

      state.isAuthenticated shouldBe false
      state.currentRoute.shouldBeInstanceOf<LoginRoute>()
    }

    test("currentBackStack returns authBackStack when unauthenticated") {
      val state = StarterNavigationState()

      state.currentBackStack shouldBe state.authBackStack
    }

    test("currentBackStack returns current tab backstack when authenticated") {
      val tabBackStacks = TopLevelTab.entries.associateWith {
        SnapshotStateList<TopLevelRoute>().apply {
          add(it.startRoute)
        }
      }
      val state = StarterNavigationState(
        isAuthenticated = true,
        tabBackStacks = tabBackStacks,
        currentTab = TopLevelTab.HOME,
      )

      state.currentBackStack shouldBe tabBackStacks[TopLevelTab.HOME]
      state.currentRoute.shouldBeInstanceOf<HomeRoute>()
    }

    test("each tab has its own backstack with correct start route") {
      val state = StarterNavigationState(isAuthenticated = true)

      state.tabBackStacks[TopLevelTab.HOME]?.first().shouldBeInstanceOf<HomeRoute>()
      state.tabBackStacks[TopLevelTab.PROFILE]?.first().shouldBeInstanceOf<ProfileRoute>()
      state.tabBackStacks[TopLevelTab.SETTINGS]?.first().shouldBeInstanceOf<SettingsRoute>()
    }

    test("default current tab is HOME") {
      val state = StarterNavigationState(isAuthenticated = true)

      state.currentTab shouldBe TopLevelTab.HOME
    }

    test("currentRoute returns last item in backstack") {
      val authBackStack = SnapshotStateList<AuthFlowRoute>().apply {
        add(LoginRoute)
        add(RegisterRoute)
      }
      val state = StarterNavigationState(authBackStack = authBackStack)

      state.currentRoute.shouldBeInstanceOf<RegisterRoute>()
    }

    test("each tab starts with exactly one route") {
      val state = StarterNavigationState(isAuthenticated = true)

      TopLevelTab.entries.forEach { tab ->
        state.tabBackStacks[tab]?.size shouldBe 1
      }
    }
  })
