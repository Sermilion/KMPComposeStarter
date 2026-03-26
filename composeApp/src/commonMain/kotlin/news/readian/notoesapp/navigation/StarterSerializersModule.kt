package news.readian.notoesapp.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import news.readian.notoesapp.common.navigation.AuthFlowRoute
import news.readian.notoesapp.common.navigation.Route
import news.readian.notoesapp.common.navigation.TopLevelRoute
import news.readian.notoesapp.feature.auth.navigation.LoginRoute
import news.readian.notoesapp.feature.auth.navigation.RegisterRoute
import news.readian.notoesapp.feature.home.navigation.HomeRoute
import news.readian.notoesapp.feature.profile.navigation.ProfileRoute
import news.readian.notoesapp.feature.settings.navigation.SettingsRoute

val starterSerializersModule = SerializersModule {
  polymorphic(NavKey::class) {
    subclass(LoginRoute::class, LoginRoute.serializer())
    subclass(RegisterRoute::class, RegisterRoute.serializer())
    subclass(HomeRoute::class, HomeRoute.serializer())
    subclass(ProfileRoute::class, ProfileRoute.serializer())
    subclass(SettingsRoute::class, SettingsRoute.serializer())
  }

  polymorphic(Route::class) {
    subclass(LoginRoute::class, LoginRoute.serializer())
    subclass(RegisterRoute::class, RegisterRoute.serializer())
    subclass(HomeRoute::class, HomeRoute.serializer())
    subclass(ProfileRoute::class, ProfileRoute.serializer())
    subclass(SettingsRoute::class, SettingsRoute.serializer())
  }

  polymorphic(AuthFlowRoute::class) {
    subclass(LoginRoute::class, LoginRoute.serializer())
    subclass(RegisterRoute::class, RegisterRoute.serializer())
  }

  polymorphic(TopLevelRoute::class) {
    subclass(HomeRoute::class, HomeRoute.serializer())
    subclass(ProfileRoute::class, ProfileRoute.serializer())
    subclass(SettingsRoute::class, SettingsRoute.serializer())
  }
}
