package news.readian.notoesapp.common.navigation

import kotlinx.serialization.modules.PolymorphicModuleBuilder

interface RouteRegistration {
  fun registerRoutes(builder: PolymorphicModuleBuilder<Route>)
}
