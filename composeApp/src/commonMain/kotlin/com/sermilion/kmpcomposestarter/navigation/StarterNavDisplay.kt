package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.core.ui.di.rememberNavEntryViewModelStoreOwner

/**
 * Renders the active back stack.
 *
 * @param entryProvider injectable so a UI test can drive the display without the real DI graph.
 */
@Composable
internal fun StarterNavDisplay(
  navigationState: StarterNavigationState,
  navigator: StarterNavigator,
  modifier: Modifier = Modifier,
  entryProvider: (Route) -> NavEntry<Route> =
    remember(navigator) { createStarterEntryProvider(navigator) },
) {
  val entryStoreOwner = rememberNavEntryViewModelStoreOwner()

  key(entryStoreOwner) {
    val decorators =
      listOf(
        rememberSaveableStateHolderNavEntryDecorator<Route>(),
        rememberSavedStateNavEntryDecorator<Route>(),
        rememberViewModelStoreNavEntryDecorator<Route>(viewModelStoreOwner = entryStoreOwner),
      )

    val tabEntries =
      TopLevelTab.entries.associateWith { tab ->
        rememberDecoratedNavEntries(
          backStack =
            navigationState.tabBackStacks[tab]?.takeIf { it.isNotEmpty() }
              ?: listOf(tab.startRoute),
          entryDecorators = decorators,
          entryProvider = entryProvider,
        )
      }
    val authEntries =
      rememberDecoratedNavEntries(
        backStack = navigationState.authBackStack,
        entryDecorators = decorators,
        entryProvider = entryProvider,
      )

    val currentTab = navigationState.currentTab
    val entries =
      if (navigationState.isAuthenticated) {
        tabEntries.getValue(currentTab)
      } else {
        authEntries
      }

    val previousTab = remember { PreviousTabHolder(currentTab) }
    val isTabSwitching = previousTab.value != currentTab
    SideEffect { previousTab.value = currentTab }

    NavDisplay(
      entries = entries,
      modifier = modifier,
      onBack = { navigator.goBack() },
      transitionSpec = {
        if (isTabSwitching) {
          fadeIn(fadeAnimationSpec) togetherWith fadeOut(fadeAnimationSpec)
        } else {
          slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = slideAnimationSpec,
          ) togetherWith
            slideOutHorizontally(
              targetOffsetX = { -it / 3 },
              animationSpec = slideAnimationSpec,
            )
        }
      },
      popTransitionSpec = { popTransition() },
      predictivePopTransitionSpec = { popTransition() },
    )
  }
}

/**
 * The previously rendered tab, held outside the snapshot system on purpose.
 *
 * A snapshot write from the [SideEffect] would invalidate this composition, and the recomposition
 * it forced would re-read `isTabSwitching` as false — swapping the tab-switch fade for a push
 * slide while the transition was still running.
 */
private class PreviousTabHolder(
  var value: TopLevelTab,
)

private fun popTransition(): ContentTransform =
  slideInHorizontally(
    initialOffsetX = { -it / 3 },
    animationSpec = slideAnimationSpec,
  ) togetherWith
    slideOutHorizontally(
      targetOffsetX = { it },
      animationSpec = slideAnimationSpec,
    )

private val fadeAnimationSpec: FiniteAnimationSpec<Float> =
  tween(
    durationMillis = 300,
    easing = FastOutSlowInEasing,
  )

private val slideAnimationSpec: FiniteAnimationSpec<IntOffset> =
  tween(
    durationMillis = 350,
    easing = FastOutSlowInEasing,
  )
