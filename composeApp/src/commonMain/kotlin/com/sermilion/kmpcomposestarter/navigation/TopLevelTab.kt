package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.common.navigation.TopLevelRoute
import com.sermilion.kmpcomposestarter.feature.home.navigation.HomeRoute
import com.sermilion.kmpcomposestarter.feature.profile.navigation.ProfileRoute
import com.sermilion.kmpcomposestarter.feature.settings.navigation.SettingsRoute
import kmpcomposestarter.composeapp.generated.resources.Res
import kmpcomposestarter.composeapp.generated.resources.tab_home
import kmpcomposestarter.composeapp.generated.resources.tab_profile
import kmpcomposestarter.composeapp.generated.resources.tab_settings
import org.jetbrains.compose.resources.StringResource

/**
 * The single description of a top-level tab: its start route, its bar icon and its bar label.
 *
 * Adding a tab is one edit here. Nothing else in the app may re-describe a tab — the bottom bar
 * reads [icon] and [label] off this enum, and [isTabRoot] is derived from [startRoute], so there
 * is no second list to keep in step.
 */
enum class TopLevelTab(
  val startRoute: TopLevelRoute,
  val icon: ImageVector,
  val label: StringResource,
) {
  HOME(HomeRoute, Icons.Default.Home, Res.string.tab_home),
  PROFILE(ProfileRoute, Icons.Default.Person, Res.string.tab_profile),
  SETTINGS(SettingsRoute, Icons.Default.Settings, Res.string.tab_settings),
}

/**
 * True when this route is some tab's start destination.
 *
 * A tab root is reachable only through [StarterNavigator.navigateToTopLevel]; pushing one onto
 * another tab's stack would give two entries the same content key and let them share — and then
 * lose — each other's saved state.
 */
val Route.isTabRoot: Boolean
  get() = TopLevelTab.entries.any { it.startRoute == this }
