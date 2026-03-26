package news.readian.notoesapp.navigation

import news.readian.notoesapp.common.navigation.TopLevelRoute
import news.readian.notoesapp.feature.home.navigation.HomeRoute
import news.readian.notoesapp.feature.profile.navigation.ProfileRoute
import news.readian.notoesapp.feature.settings.navigation.SettingsRoute

enum class TopLevelTab(val startRoute: TopLevelRoute) {
  HOME(HomeRoute),
  PROFILE(ProfileRoute),
  SETTINGS(SettingsRoute),
}
