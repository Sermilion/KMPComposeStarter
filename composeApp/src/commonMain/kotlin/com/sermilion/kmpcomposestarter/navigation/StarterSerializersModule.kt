package com.sermilion.kmpcomposestarter.navigation

import androidx.navigation3.runtime.NavKey
import com.sermilion.kmpcomposestarter.common.navigation.AuthFlowRoute
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.common.navigation.TopLevelRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.LoginRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.RegisterRoute
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailRoute
import com.sermilion.kmpcomposestarter.feature.home.navigation.HomeRoute
import com.sermilion.kmpcomposestarter.feature.profile.navigation.ProfileRoute
import com.sermilion.kmpcomposestarter.feature.settings.navigation.SettingsRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

/**
 * The polymorphic registry behind [StarterNavigationStateSaver].
 *
 * Every route the entry provider can render must appear here, or restoring after process death
 * throws on it. `StarterRouteSerializationTest` fails when the two lists drift apart.
 */
val starterSerializersModule = SerializersModule {
  polymorphic(NavKey::class) {
    subclass(LoginRoute::class, LoginRoute.serializer())
    subclass(RegisterRoute::class, RegisterRoute.serializer())
    subclass(HomeRoute::class, HomeRoute.serializer())
    subclass(DetailRoute::class, DetailRoute.serializer())
    subclass(ProfileRoute::class, ProfileRoute.serializer())
    subclass(SettingsRoute::class, SettingsRoute.serializer())
  }

  polymorphic(Route::class) {
    subclass(LoginRoute::class, LoginRoute.serializer())
    subclass(RegisterRoute::class, RegisterRoute.serializer())
    subclass(HomeRoute::class, HomeRoute.serializer())
    subclass(DetailRoute::class, DetailRoute.serializer())
    subclass(ProfileRoute::class, ProfileRoute.serializer())
    subclass(SettingsRoute::class, SettingsRoute.serializer())
  }

  polymorphic(AuthFlowRoute::class) {
    subclass(LoginRoute::class, LoginRoute.serializer())
    subclass(RegisterRoute::class, RegisterRoute.serializer())
  }

  polymorphic(TopLevelRoute::class) {
    subclass(HomeRoute::class, HomeRoute.serializer())
    subclass(DetailRoute::class, DetailRoute.serializer())
    subclass(ProfileRoute::class, ProfileRoute.serializer())
    subclass(SettingsRoute::class, SettingsRoute.serializer())
  }
}
