package com.sermilion.kmpcomposestarter.feature.auth.navigation

import com.sermilion.kmpcomposestarter.common.navigation.AuthFlowRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute : AuthFlowRoute

@Serializable
data object RegisterRoute : AuthFlowRoute
