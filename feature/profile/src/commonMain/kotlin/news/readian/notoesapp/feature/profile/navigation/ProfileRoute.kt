package news.readian.notoesapp.feature.profile.navigation

import kotlinx.serialization.Serializable
import news.readian.notoesapp.common.navigation.TopLevelRoute
import news.readian.notoesapp.common.navigation.UserScopedRoute

@Serializable
data object ProfileRoute : TopLevelRoute, UserScopedRoute
