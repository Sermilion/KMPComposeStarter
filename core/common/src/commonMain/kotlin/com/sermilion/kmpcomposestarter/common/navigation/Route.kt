package com.sermilion.kmpcomposestarter.common.navigation

import androidx.navigation3.runtime.NavKey

/** Anything the app can render as a destination. */
interface Route : NavKey

/**
 * A destination on the signed-in side of the app.
 *
 * "Main flow", not "top level": a route is a [MainFlowRoute] because it belongs to the
 * authenticated stacks, not because it is a tab root. Detail screens are [MainFlowRoute]s too.
 * Whether a route is some tab's start destination is a separate question, answered by
 * `Route.isTabRoot`.
 */
interface MainFlowRoute : Route

/** A destination on the signed-out side of the app: login, registration, recovery. */
interface AuthFlowRoute : Route
