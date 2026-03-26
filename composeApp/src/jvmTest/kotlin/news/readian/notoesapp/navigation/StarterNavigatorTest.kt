
package news.readian.notoesapp.navigation

import androidx.compose.runtime.mutableStateOf
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import news.readian.notoesapp.feature.auth.navigation.RegisterRoute
import news.readian.notoesapp.feature.home.navigation.HomeRoute
import news.readian.notoesapp.feature.onboarding.navigation.TutorialRoute
import news.readian.notoesapp.feature.profile.navigation.ProfileRoute
import news.readian.notoesapp.feature.settings.navigation.SettingsRoute

class StarterNavigatorTest :
  FunSpec({
    test("initial state should have TutorialRoute as current route") {
      val state = mutableStateOf(StarterNavigationState())
      StarterNavigator(state)

      state.value.currentRoute.shouldBeInstanceOf<TutorialRoute>()
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
      state.value.currentRoute.shouldBeInstanceOf<TutorialRoute>()
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
  })
