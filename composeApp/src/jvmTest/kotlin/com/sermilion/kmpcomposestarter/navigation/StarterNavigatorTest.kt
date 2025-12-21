package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.mutableStateOf
import com.sermilion.kmpcomposestarter.feature.auth.navigation.LoginRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.RegisterRoute
import com.sermilion.kmpcomposestarter.feature.home.navigation.HomeRoute
import com.sermilion.kmpcomposestarter.feature.profile.navigation.ProfileRoute
import com.sermilion.kmpcomposestarter.feature.settings.navigation.SettingsRoute
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

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

      navigator.navigate(RegisterRoute)

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
      navigator.navigate(ProfileRoute)

      state.value.tabBackStacks[TopLevelTab.HOME]?.size shouldBe 2

      navigator.navigateToTopLevel(TopLevelTab.HOME)

      state.value.tabBackStacks[TopLevelTab.HOME]?.size shouldBe 1
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

    test("tab backstacks are preserved when switching tabs") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.onLoginStateChanged(true)

      navigator.navigateToTopLevel(TopLevelTab.PROFILE)
      navigator.navigateToTopLevel(TopLevelTab.HOME)

      state.value.tabBackStacks[TopLevelTab.PROFILE]?.size shouldBe 1
      state.value.tabBackStacks[TopLevelTab.HOME]?.size shouldBe 1
    }

    test("navigate within tab adds to current tab backstack") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.onLoginStateChanged(true)

      navigator.navigate(ProfileRoute)

      state.value.currentTab shouldBe TopLevelTab.HOME
      state.value.tabBackStacks[TopLevelTab.HOME]?.size shouldBe 2
      state.value.currentRoute.shouldBeInstanceOf<ProfileRoute>()
    }

    test("goBack in tab flow removes last route") {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)
      navigator.onLoginStateChanged(true)
      navigator.navigate(ProfileRoute)

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
