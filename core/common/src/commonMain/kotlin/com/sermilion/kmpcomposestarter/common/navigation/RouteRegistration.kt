package com.sermilion.kmpcomposestarter.common.navigation

import kotlinx.serialization.modules.PolymorphicModuleBuilder

interface RouteRegistration {
  fun registerRoutes(builder: PolymorphicModuleBuilder<Route>)
}
