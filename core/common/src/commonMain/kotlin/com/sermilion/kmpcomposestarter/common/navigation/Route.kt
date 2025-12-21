package com.sermilion.kmpcomposestarter.common.navigation

import androidx.navigation3.runtime.NavKey

interface Route : NavKey

interface TopLevelRoute : Route

interface AuthFlowRoute : Route

interface UserScopedRoute
