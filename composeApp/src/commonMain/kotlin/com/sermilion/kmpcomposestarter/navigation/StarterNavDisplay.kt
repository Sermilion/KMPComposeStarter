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
fun StarterNavDisplay(
  navigationState: StarterNavigationState,
  navigator: StarterNavigator,
  modifier: Modifier = Modifier,
  entryProvider: (Route) -> NavEntry<Route> = createStarterEntryProvider(navigator),
) {
  // Nav-entry state is keyed by route, so without a session-scoped owner the next user's Home
  // entry would inherit the previous session's screen component, ViewModels and saved UI state.
  // The owner changes only when a session ends; keying on it discards the entries with it.
  val entryStoreOwner = rememberNavEntryViewModelStoreOwner()

  key(entryStoreOwner) {
    val decorators = listOf(
      // Order is load bearing: saveable state outermost, then the per-entry SavedStateRegistry
      // owner it scopes, then the ViewModel stores that read that owner.
      rememberSaveableStateHolderNavEntryDecorator<Route>(),
      rememberSavedStateNavEntryDecorator<Route>(),
      rememberViewModelStoreNavEntryDecorator<Route>(viewModelStoreOwner = entryStoreOwner),
    )

    // One rememberDecoratedNavEntries per back stack, which is what navigation3-runtime documents
    // for multiple back stacks. Entries absent from the list handed to NavDisplay are popped, so
    // the previous single-display design — one display fed a wholesale-swapped list — cleared
    // every tab's ViewModelStore and SaveableStateHolder on each switch. These calls are
    // unconditional over a fixed enum precisely so an off-screen tab keeps its entries alive.
    val tabEntries = TopLevelTab.entries.associateWith { tab ->
      rememberDecoratedNavEntries(
        backStack = navigationState.tabBackStacks[tab]?.takeIf { it.isNotEmpty() }
          ?: listOf(tab.startRoute),
        entryDecorators = decorators,
        entryProvider = entryProvider,
      )
    }
    val authEntries = rememberDecoratedNavEntries(
      backStack = navigationState.authBackStack,
      entryDecorators = decorators,
      entryProvider = entryProvider,
    )

    val currentTab = navigationState.currentTab
    val entries = if (navigationState.isAuthenticated) {
      tabEntries.getValue(currentTab)
    } else {
      authEntries
    }

    // Tab switches are classified by the tab actually changing. Reading it off the destination
    // ("is a root and the stack is one deep") mislabelled every switch into a tab whose stack was
    // deeper than one, and every pop back to a root as a tab switch.
    val previousTab = remember { PreviousTabHolder(currentTab) }
    val isTabSwitching = previousTab.value != currentTab
    SideEffect { previousTab.value = currentTab }

    NavDisplay(
      entries = entries,
      modifier = modifier,
      // The library default pops the back stack list directly, which would leave the navigator's
      // copy of the state behind. Every mutation goes through the navigator, including this one.
      onBack = { navigator.goBack() },
      transitionSpec = {
        if (isTabSwitching) {
          fadeIn(fadeAnimationSpec) togetherWith fadeOut(fadeAnimationSpec)
        } else {
          slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = slideAnimationSpec,
          ) togetherWith slideOutHorizontally(
            targetOffsetX = { -it / 3 },
            animationSpec = slideAnimationSpec,
          )
        }
      },
      popTransitionSpec = { popTransition() },
      // Gesture back and button back share one spec by construction, so they cannot drift.
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
private class PreviousTabHolder(var value: TopLevelTab)

private fun popTransition(): ContentTransform =
  slideInHorizontally(
    initialOffsetX = { -it / 3 },
    animationSpec = slideAnimationSpec,
  ) togetherWith slideOutHorizontally(
    targetOffsetX = { it },
    animationSpec = slideAnimationSpec,
  )

private val fadeAnimationSpec: FiniteAnimationSpec<Float> = tween(
  durationMillis = 300,
  easing = FastOutSlowInEasing,
)

private val slideAnimationSpec: FiniteAnimationSpec<IntOffset> = tween(
  durationMillis = 350,
  easing = FastOutSlowInEasing,
)
