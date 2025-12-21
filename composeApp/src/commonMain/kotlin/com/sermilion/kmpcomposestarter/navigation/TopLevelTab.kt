package com.sermilion.kmpcomposestarter.navigation

import com.sermilion.kmpcomposestarter.common.navigation.TopLevelRoute
import com.sermilion.kmpcomposestarter.feature.home.navigation.HomeRoute
import com.sermilion.kmpcomposestarter.feature.profile.navigation.ProfileRoute
import com.sermilion.kmpcomposestarter.feature.settings.navigation.SettingsRoute

enum class TopLevelTab(val startRoute: TopLevelRoute) {
  HOME(HomeRoute),
  PROFILE(ProfileRoute),
  SETTINGS(SettingsRoute),
}
