package news.readian.notoesapp.feature.auth.navigation

import kotlinx.serialization.Serializable
import news.readian.notoesapp.common.navigation.AuthFlowRoute

@Serializable
data object LoginRoute : AuthFlowRoute

@Serializable
data object RegisterRoute : AuthFlowRoute
