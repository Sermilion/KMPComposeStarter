package news.readian.notoesapp.common.navigation

import androidx.navigation3.runtime.NavKey

interface Route : NavKey

interface TopLevelRoute : Route

interface AuthFlowRoute : Route

interface UserScopedRoute
