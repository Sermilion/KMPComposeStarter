package com.sermilion.kmpcomposestarter.feature.profile.navigation

import com.sermilion.kmpcomposestarter.common.navigation.TopLevelRoute
import com.sermilion.kmpcomposestarter.common.navigation.UserScopedRoute
import kotlinx.serialization.Serializable

@Serializable
data object ProfileRoute : TopLevelRoute, UserScopedRoute
