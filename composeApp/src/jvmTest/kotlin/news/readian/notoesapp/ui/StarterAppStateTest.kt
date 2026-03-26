
package news.readian.notoesapp.ui

import androidx.compose.runtime.mutableStateOf
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import news.readian.notoesapp.feature.home.navigation.HomeRoute
import news.readian.notoesapp.feature.onboarding.navigation.TutorialRoute
import news.readian.notoesapp.navigation.StarterNavigationState
import news.readian.notoesapp.navigation.StarterNavigator

class StarterAppStateTest :
  FunSpec({
    test(
      "syncAuthenticationState promotes auth flow to home when authenticated content is available",
    ) {
      val state = mutableStateOf(StarterNavigationState())
      val navigator = StarterNavigator(state)

      syncAuthenticationState(
        navigator = navigator,
        canShowAuthenticated = true,
        currentIsAuthenticated = false,
      )

      state.value.isAuthenticated shouldBe true
      state.value.currentRoute.shouldBeInstanceOf<HomeRoute>()
    }

    test(
      "syncAuthenticationState resets to tutorial route when authenticated content is unavailable",
    ) {
      val state = mutableStateOf(StarterNavigationState(isAuthenticated = true))
      val navigator = StarterNavigator(state)

      syncAuthenticationState(
        navigator = navigator,
        canShowAuthenticated = false,
        currentIsAuthenticated = true,
      )

      state.value.isAuthenticated shouldBe false
      state.value.currentRoute.shouldBeInstanceOf<TutorialRoute>()
    }
  })
