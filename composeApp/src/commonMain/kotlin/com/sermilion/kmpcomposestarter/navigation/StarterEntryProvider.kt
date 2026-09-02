package com.sermilion.kmpcomposestarter.navigation

import androidx.navigation3.runtime.entryProvider
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.feature.auth.navigation.RegisterRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.authEntries
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailRoute
import com.sermilion.kmpcomposestarter.feature.home.navigation.homeEntries
import com.sermilion.kmpcomposestarter.feature.profile.navigation.profileEntries
import com.sermilion.kmpcomposestarter.feature.settings.navigation.settingsEntries

/**
 * The one place a feature's screens are joined to this app's back stack.
 *
 * Each feature registers its own routes; the shell supplies the navigation each one asks for. That
 * keeps the route-to-screen binding next to the screen it names, and keeps the back stack rules in
 * the navigator - a feature never pushes onto a stack it does not own.
 *
 * Every feature is registered unconditionally: which entries are reachable is decided by the back
 * stack the display is handed, not by a gate here, so a signed-out user can no more reach
 * [com.sermilion.kmpcomposestarter.feature.home.navigation.HomeRoute] than a registration that
 * forgot its entry can silently render nothing.
 *
 * Any route registered by a feature must also appear in [starterSerializersModule], or the first
 * process-death restore fails on it. `StarterRouteSerializationTest` is the check for that.
 */
internal fun createStarterEntryProvider(navigator: StarterNavigator) =
  entryProvider<Route> {
    authEntries(
      onNavigateToRegister = { navigator.navigate(RegisterRoute) },
      onNavigateBack = { navigator.goBack() },
    )
    homeEntries(
      onNavigateToProfile = { navigator.navigateToTopLevel(TopLevelTab.PROFILE) },
      onOpenDetail = { id -> navigator.navigate(DetailRoute(id)) },
      onNavigateBack = { navigator.goBack() },
    )
    profileEntries(onNavigateBack = { navigator.goBack() })
    settingsEntries()
  }
