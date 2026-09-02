package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.saveable.Saver
import com.sermilion.kmpcomposestarter.common.navigation.AuthFlowRoute
import com.sermilion.kmpcomposestarter.common.navigation.TopLevelRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.LoginRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

/**
 * Saves the whole navigation state — stack, tab and auth flag — across configuration change and
 * process death.
 *
 * The encoding goes through [starterSerializersModule], which is what makes that module load
 * bearing: a route registered in [createStarterEntryProvider] but forgotten there throws on save,
 * in development, rather than degrading silently. Restore is deliberately the lenient direction —
 * see the comment on it.
 */
internal val StarterNavigationStateSaver: Saver<StarterNavigationState, String> =
  Saver(
    save = { encodeNavigationState(it) },
    restore = { encoded ->
      // A payload written before a route changed shape can no longer decode. Letting that throw
      // out of a saver turns an app update into a crash loop the user cannot clear, so an
      // undecodable payload restores as null and `rememberSaveable` falls back to a fresh state.
      try {
        decodeNavigationState(encoded)
      } catch (error: SerializationException) {
        null
      }
    },
  )

/** Plain, composition-free half of [StarterNavigationStateSaver], so the round trip is testable. */
internal fun encodeNavigationState(state: StarterNavigationState): String =
  navigationStateJson.encodeToString(
    SavedNavigationState(
      isAuthenticated = state.isAuthenticated,
      authBackStack = state.authBackStack.toList(),
      tabBackStacks =
        state.tabBackStacks.entries.associate { (tab, stack) ->
          tab.name to stack.toList()
        },
      currentTab = state.currentTab.name,
    ),
  )

internal fun decodeNavigationState(encoded: String): StarterNavigationState {
  val saved = navigationStateJson.decodeFromString<SavedNavigationState>(encoded)
  return StarterNavigationState(
    isAuthenticated = saved.isAuthenticated,
    // An empty stack is not a state any display can render, so a truncated payload falls back to
    // the start of the flow rather than crashing on restore.
    authBackStack =
      saved.authBackStack.takeIf { it.isNotEmpty() }?.toSnapshotStateList()
        ?: snapshotStateListOf(LoginRoute),
    tabBackStacks =
      TopLevelTab.entries.associateWith { tab ->
        // A stack that is missing or empty in the restored payload falls back to the tab's start
        // route: an empty back stack is not a state any screen can render.
        saved.tabBackStacks[tab.name]
          ?.takeIf { it.isNotEmpty() }
          ?.toSnapshotStateList()
          ?: snapshotStateListOf(tab.startRoute)
      },
    currentTab =
      TopLevelTab.entries.firstOrNull { it.name == saved.currentTab }
        ?: TopLevelTab.HOME,
  )
}

/**
 * Tabs are keyed by [TopLevelTab.name] rather than by the enum itself so that a tab removed from a
 * fork's enum drops out of an old payload instead of failing the whole restore.
 */
@Serializable
private data class SavedNavigationState(
  val isAuthenticated: Boolean,
  val authBackStack: List<AuthFlowRoute>,
  val tabBackStacks: Map<String, List<TopLevelRoute>>,
  val currentTab: String,
)

private val navigationStateJson =
  Json {
    serializersModule = starterSerializersModule
    // A field dropped from a route in a later build must not fail the whole restore over one key.
    ignoreUnknownKeys = true
  }
