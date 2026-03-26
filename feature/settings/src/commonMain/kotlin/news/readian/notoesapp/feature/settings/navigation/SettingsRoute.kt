package news.readian.notoesapp.feature.settings.navigation

import kotlinx.serialization.Serializable
import news.readian.notoesapp.common.navigation.TopLevelRoute
import news.readian.notoesapp.common.navigation.UserScopedRoute

@Serializable
data object SettingsRoute : TopLevelRoute, UserScopedRoute
