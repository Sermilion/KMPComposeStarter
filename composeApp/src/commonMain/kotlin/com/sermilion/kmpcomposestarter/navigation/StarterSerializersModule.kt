package com.sermilion.kmpcomposestarter.navigation

import androidx.navigation3.runtime.NavKey
import com.sermilion.kmpcomposestarter.common.navigation.AuthFlowRoute
import com.sermilion.kmpcomposestarter.common.navigation.MainFlowRoute
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.feature.auth.navigation.LoginRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.RegisterRoute
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailRoute
import com.sermilion.kmpcomposestarter.feature.home.navigation.HomeRoute
import com.sermilion.kmpcomposestarter.feature.profile.navigation.ProfileRoute
import com.sermilion.kmpcomposestarter.feature.settings.navigation.SettingsRoute
import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.reflect.KClass

/** Routes reachable while signed out. */
private val authRoutes: List<RouteRegistration<out AuthFlowRoute>> =
  listOf(
    RouteRegistration(LoginRoute::class, LoginRoute.serializer()),
    RouteRegistration(RegisterRoute::class, RegisterRoute.serializer()),
  )

/** Routes reachable while signed in, tab roots and the screens pushed on top of them alike. */
private val mainRoutes: List<RouteRegistration<out MainFlowRoute>> =
  listOf(
    RouteRegistration(HomeRoute::class, HomeRoute.serializer()),
    RouteRegistration(DetailRoute::class, DetailRoute.serializer()),
    RouteRegistration(ProfileRoute::class, ProfileRoute.serializer()),
    RouteRegistration(SettingsRoute::class, SettingsRoute.serializer()),
  )

/**
 * The polymorphic registry behind [StarterNavigationStateSaver].
 *
 * Every route is written down exactly once, in [authRoutes] or [mainRoutes], and registered into
 * each hierarchy that can hold it by the loops below. The four hand-maintained `polymorphic` blocks
 * this replaced listed the same six routes up to four times each, so adding a route meant four
 * edits and forgetting one only surfaced on a process-death restore.
 *
 * Declared after the two lists on purpose: top-level properties initialize in file order, and a
 * registry built above them would read them as null.
 *
 * `StarterRouteSerializationTest` still checks that these lists and the entry provider agree.
 */
internal val starterSerializersModule =
  SerializersModule {
    val everyRoute = authRoutes + mainRoutes

    polymorphic(NavKey::class) { everyRoute.forEach { register(it) } }
    polymorphic(Route::class) { everyRoute.forEach { register(it) } }

    polymorphic(AuthFlowRoute::class) { authRoutes.forEach { register(it) } }
    polymorphic(MainFlowRoute::class) { mainRoutes.forEach { register(it) } }
  }

/**
 * One route's class paired with its serializer.
 *
 * Typed rather than erased, so [register] needs no unchecked cast. What a registration may hold is
 * pinned by the element type of [authRoutes] and [mainRoutes], not by a bound here: Kotlin forbids
 * a second bound on a type parameter that is already bounded by another type parameter, which is
 * what [register] needs to check the route against the hierarchy it is being filed under.
 */
private class RouteRegistration<T : Any>(
  val kClass: KClass<T>,
  val serializer: KSerializer<T>,
)

/**
 * `T : Base` is what keeps this honest — a route can only be registered under a base class it
 * actually extends, so no loop above can quietly file a route in the wrong hierarchy. `Base` is
 * only `Any` because `NavKey`, one of the four hierarchies, sits above `Route` rather than below
 * it.
 */
private fun <Base : Any, T : Base> PolymorphicModuleBuilder<Base>.register(
  registration: RouteRegistration<T>,
) = subclass(registration.kClass, registration.serializer)
