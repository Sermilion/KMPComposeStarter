package com.sermilion.kmpcomposestarter.feature.home.detail

import com.sermilion.kmpcomposestarter.common.navigation.TopLevelRoute
import kotlinx.serialization.Serializable

/**
 * The template's worked example of a parameterised route.
 *
 * It carries the id and nothing else: the detail screen's ViewModel is the layer that resolves it,
 * so a stale copy of a domain object can never travel through the back stack. It is a
 * [TopLevelRoute] so it can be pushed onto a tab stack, and deliberately not any tab's start
 * route — `StarterNavigator.navigate` rejects those.
 */
@Serializable
data class DetailRoute(val id: String) : TopLevelRoute
