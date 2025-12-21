package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.feature.home.navigation.HomeRoute
import com.sermilion.kmpcomposestarter.feature.profile.navigation.ProfileRoute
import com.sermilion.kmpcomposestarter.feature.settings.navigation.SettingsRoute

@Composable
fun StarterNavDisplay(
  navigationState: StarterNavigationState,
  navigator: StarterNavigator,
  isLoggedIn: Boolean,
  modifier: Modifier = Modifier,
) {
  val savedStateDecorator = rememberSaveableStateHolderNavEntryDecorator<Route>()
  val viewModelDecorator = rememberViewModelStoreNavEntryDecorator<Route>()

  val backStack = navigationState.getBackStack(isLoggedIn)
  val currentRoute = backStack.lastOrNull()
  val isRootScreen = remember(currentRoute) {
    currentRoute?.isTabRoot == true
  }

  val isTabSwitching = isRootScreen && backStack.size == 1

  NavDisplay(
    backStack = backStack,
    modifier = modifier,
    entryProvider = createStarterEntryProvider(
      navigator = navigator,
      isLoggedIn = isLoggedIn,
    ),
    entryDecorators = listOf(savedStateDecorator, viewModelDecorator),
    transitionSpec = {
      when {
        isTabSwitching -> fadeIn(fadeAnimationSpec) togetherWith fadeOut(fadeAnimationSpec)
        else -> slideInHorizontally(
          initialOffsetX = { it },
          animationSpec = slideAnimationSpec,
        ) togetherWith slideOutHorizontally(
          targetOffsetX = { -it / 3 },
          animationSpec = slideAnimationSpec,
        )
      }
    },
    popTransitionSpec = {
      slideInHorizontally(
        initialOffsetX = { -it / 3 },
        animationSpec = slideAnimationSpec,
      ) togetherWith slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = slideAnimationSpec,
      )
    },
  )
}

val Route.isTabRoot: Boolean
  get() = this is HomeRoute || this is ProfileRoute || this is SettingsRoute

@Composable
private fun <T : Any> rememberSaveableStateHolderNavEntryDecorator(): NavEntryDecorator<T> {
  val saveableStateHolder = rememberSaveableStateHolder()
  return remember(saveableStateHolder) {
    NavEntryDecorator(
      onPop = { contentKey -> saveableStateHolder.removeState(contentKey) },
    ) { entry: NavEntry<T> ->
      saveableStateHolder.SaveableStateProvider(key = entry.contentKey) {
        entry.Content()
      }
    }
  }
}

private val fadeAnimationSpec: FiniteAnimationSpec<Float> = tween(
  durationMillis = 300,
  easing = FastOutSlowInEasing,
)

private val slideAnimationSpec: FiniteAnimationSpec<IntOffset> = tween(
  durationMillis = 350,
  easing = FastOutSlowInEasing,
)
