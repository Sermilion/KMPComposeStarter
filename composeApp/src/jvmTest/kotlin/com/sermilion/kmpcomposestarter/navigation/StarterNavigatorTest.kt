package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.mutableStateOf
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.feature.auth.navigation.LoginRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.RegisterRoute
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailRoute
import com.sermilion.kmpcomposestarter.feature.home.navigation.HomeRoute
import com.sermilion.kmpcomposestarter.feature.profile.navigation.ProfileRoute
import com.sermilion.kmpcomposestarter.feature.settings.navigation.SettingsRoute
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs

class StarterNavigatorTest :
  FunSpec({

    test("initial state should have LoginRoute as current route") {
      val state = mutableStateOf(StarterNavigationState())
      StarterNavigator(state)

      state.value.currentRoute.shouldBeInstanceOf<LoginRoute>()
      state.value.isAuthenticated shouldBe false
    }

    test("navigate to RegisterRoute from auth flow") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)

      navigator.navigate(RegisterRoute) shouldBe true

      state.value.authBackStack.size shouldBe 2
      state.value.currentRoute.shouldBeInstanceOf<RegisterRoute>()
    }

    test("goBack in auth flow removes last route") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.navigate(RegisterRoute)

      val result = navigator.goBack()

      result shouldBe true
      state.value.authBackStack.size shouldBe 1
      state.value.currentRoute.shouldBeInstanceOf<LoginRoute>()
    }

    test("goBack returns false when only one route in auth stack") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)

      val result = navigator.goBack()

      result shouldBe false
    }

    test("onLoginStateChanged to true switches to authenticated state") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)

      navigator.onLoginStateChanged(true)

      state.value.isAuthenticated shouldBe true
      state.value.currentTab shouldBe TopLevelTab.HOME
      state.value.currentRoute.shouldBeInstanceOf<HomeRoute>()
    }

    test("navigateToTopLevel switches tabs") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.onLoginStateChanged(true)

      navigator.navigateToTopLevel(TopLevelTab.PROFILE)

      state.value.currentTab shouldBe TopLevelTab.PROFILE
      state.value.currentRoute.shouldBeInstanceOf<ProfileRoute>()
    }

    test("navigateToTopLevel to settings tab") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.onLoginStateChanged(true)

      navigator.navigateToTopLevel(TopLevelTab.SETTINGS)

      state.value.currentTab shouldBe TopLevelTab.SETTINGS
      state.value.currentRoute.shouldBeInstanceOf<SettingsRoute>()
    }

    test("navigateToTopLevel to same tab with stack > 1 resets stack") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.onLoginStateChanged(true)
      navigator.navigate(DetailRoute("item-1"))

      state.value.tabBackStacks[TopLevelTab.HOME]?.size shouldBe 2

      navigator.navigateToTopLevel(TopLevelTab.HOME)

      state.value.tabBackStacks[TopLevelTab.HOME]?.size shouldBe 1
    }

    test("navigate pushes a parameterised route onto the current tab") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.onLoginStateChanged(true)

      navigator.navigate(DetailRoute("item-1")) shouldBe true

      state.value.currentTab shouldBe TopLevelTab.HOME
      state.value.tabBackStacks[TopLevelTab.HOME]?.size shouldBe 2
      state.value.currentRoute shouldBe DetailRoute("item-1")
    }

    test("navigate to another tab's root is rejected and leaves the stack untouched") {
      val state = mutableStateOf(StarterNavigationState())
      val rejections = mutableListOf<Pair<Route, Boolean>>()
      val navigator =
        StarterNavigator(state) { route, rejectedIn ->
          rejections += route to rejectedIn.isAuthenticated
        }
      navigator.onLoginStateChanged(true)
      val homeStackBefore = state.value.tabBackStacks.getValue(TopLevelTab.HOME)

      navigator.navigate(ProfileRoute) shouldBe false

      rejections shouldBe listOf<Pair<Route, Boolean>>(ProfileRoute to true)
      state.value.tabBackStacks.getValue(TopLevelTab.HOME) shouldBeSameInstanceAs homeStackBefore
      state.value.tabBackStacks
        .getValue(TopLevelTab.HOME)
        .size shouldBe 1
    }

    test("navigate to an auth route while signed in is rejected") {
      val state = mutableStateOf(StarterNavigationState())
      val rejections = mutableListOf<Route>()
      val navigator = StarterNavigator(state) { route, _ -> rejections += route }
      navigator.onLoginStateChanged(true)

      navigator.navigate(RegisterRoute) shouldBe false

      rejections shouldBe listOf<Route>(RegisterRoute)
      state.value.tabBackStacks
        .getValue(TopLevelTab.HOME)
        .size shouldBe 1
      state.value.currentRoute.shouldBeInstanceOf<HomeRoute>()
    }

    test("navigate to a top-level route while signed out is rejected") {
      val state = mutableStateOf(StarterNavigationState())
      val rejections = mutableListOf<Route>()
      val navigator = StarterNavigator(state) { route, _ -> rejections += route }

      navigator.navigate(DetailRoute("item-1")) shouldBe false

      rejections shouldBe listOf<Route>(DetailRoute("item-1"))
      state.value.authBackStack.size shouldBe 1
    }

    test("onLoginStateChanged to false resets to auth flow") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.onLoginStateChanged(true)
      navigator.navigateToTopLevel(TopLevelTab.PROFILE)

      navigator.onLoginStateChanged(false)

      state.value.isAuthenticated shouldBe false
      state.value.currentRoute.shouldBeInstanceOf<LoginRoute>()
    }

    test("signing out leaves nothing of the previous session's tabs behind") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.onLoginStateChanged(true)
      navigator.navigateToTopLevel(TopLevelTab.PROFILE)
      navigator.navigate(DetailRoute("item-1"))

      navigator.onLoginStateChanged(false)

      state.value.currentTab shouldBe TopLevelTab.HOME
      TopLevelTab.entries.forEach { tab ->
        state.value.tabBackStacks
          .getValue(tab)
          .size shouldBe 1
      }
    }

    test("tab backstacks are preserved when switching tabs") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.onLoginStateChanged(true)
      val profileStack = state.value.tabBackStacks.getValue(TopLevelTab.PROFILE)
      val homeStack = state.value.tabBackStacks.getValue(TopLevelTab.HOME)

      navigator.navigateToTopLevel(TopLevelTab.PROFILE)
      navigator.navigateToTopLevel(TopLevelTab.HOME)

      state.value.tabBackStacks.getValue(TopLevelTab.PROFILE) shouldBeSameInstanceAs profileStack
      state.value.tabBackStacks.getValue(TopLevelTab.HOME) shouldBeSameInstanceAs homeStack
    }

    test("goBack in tab flow removes last route") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.onLoginStateChanged(true)
      navigator.navigate(DetailRoute("item-1"))

      val result = navigator.goBack()

      result shouldBe true
      state.value.tabBackStacks[TopLevelTab.HOME]?.size shouldBe 1
      state.value.currentRoute.shouldBeInstanceOf<HomeRoute>()
    }

    test("goBack in tab returns false when only one route in stack") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.onLoginStateChanged(true)

      val result = navigator.goBack()

      result shouldBe false
    }
  })
