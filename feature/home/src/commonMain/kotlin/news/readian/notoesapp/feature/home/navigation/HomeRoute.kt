package news.readian.notoesapp.feature.home.navigation

import kotlinx.serialization.Serializable
import news.readian.notoesapp.common.navigation.TopLevelRoute
import news.readian.notoesapp.common.navigation.UserScopedRoute

@Serializable
data object HomeRoute : TopLevelRoute, UserScopedRoute
