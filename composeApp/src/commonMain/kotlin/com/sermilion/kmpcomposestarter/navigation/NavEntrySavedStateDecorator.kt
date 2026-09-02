package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.savedstate.SavedState
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.compose.LocalSavedStateRegistryOwner
import androidx.savedstate.savedState

/**
 * Gives every nav entry its own [SavedStateRegistryOwner], which is what makes `SavedStateHandle`
 * usable in an entry-scoped ViewModel.
 *
 * `ViewModelStoreNavEntryDecorator` builds its per-entry owner by delegating to whatever
 * `LocalSavedStateRegistryOwner` resolves to and requires that owner's lifecycle to still be
 * `INITIALIZED`. Its failure message points at a `SavedStateNavEntryDecorator` that
 * navigation3-runtime 1.0.0 does not ship, and its own saveable-state decorator does not provide
 * the owner despite documenting that it does. Without this decorator the host window's owner is
 * used instead: on Android that lifecycle is already past `INITIALIZED`, and on desktop and iOS
 * the composition local has no value at all, so the first entry fails before it renders.
 *
 * Order matters: this must sit after the saveable-state decorator (so [rememberSaveable] below is
 * already scoped to the entry) and before the ViewModel-store decorator (which reads the owner).
 *
 * shortcut: hand-rolled per-entry owner; delete it in favour of the library's own
 * `SavedStateNavEntryDecorator` once navigation3-runtime ships one.
 */
@Composable
internal fun <T : Any> rememberSavedStateNavEntryDecorator(): NavEntryDecorator<T> =
  remember {
    NavEntryDecorator { entry ->
      val owner =
        rememberSaveable(saver = NavEntrySavedStateRegistryOwner.Saver) {
          NavEntrySavedStateRegistryOwner(restoredState = null)
        }
      CompositionLocalProvider(LocalSavedStateRegistryOwner provides owner) {
        entry.Content()
      }
    }
  }

private class NavEntrySavedStateRegistryOwner(
  restoredState: SavedState?,
) : SavedStateRegistryOwner {
  override val lifecycle: Lifecycle = InitializedLifecycle()

  private val controller = SavedStateRegistryController.create(this)

  override val savedStateRegistry: SavedStateRegistry
    get() = controller.savedStateRegistry

  init {
    controller.performRestore(restoredState)
  }

  companion object {
    val Saver: Saver<NavEntrySavedStateRegistryOwner, SavedState> =
      Saver(
        save = { owner -> savedState().also(owner.controller::performSave) },
        restore = { NavEntrySavedStateRegistryOwner(restoredState = it) },
      )
  }
}

/**
 * A [Lifecycle] pinned to [Lifecycle.State.INITIALIZED].
 *
 * A nav entry has no lifecycle of its own here, and the two observers that get registered against
 * this one both want the same thing it already gives them: the saved-state registry only listens
 * for `ON_START`/`ON_STOP` to gate saving, which defaults to allowed, and the SavedStateHandle
 * attacher only re-restores on `ON_CREATE`, which the registry does lazily on first read anyway.
 */
private class InitializedLifecycle : Lifecycle() {
  override val currentState: State = State.INITIALIZED

  override fun addObserver(observer: LifecycleObserver) = Unit

  override fun removeObserver(observer: LifecycleObserver) = Unit
}
