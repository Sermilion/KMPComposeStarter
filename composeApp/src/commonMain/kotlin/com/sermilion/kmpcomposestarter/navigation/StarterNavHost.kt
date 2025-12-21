package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.SavedStateConfiguration
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.sermilion.kmpcomposestarter.common.di.LocalScreenComponentFactory
import com.sermilion.kmpcomposestarter.core.data.repository.AuthRepository
import com.sermilion.kmpcomposestarter.core.domain.di.UserDependencies
import com.sermilion.kmpcomposestarter.feature.auth.ui.LoginScreen
import com.sermilion.kmpcomposestarter.feature.auth.ui.RegisterScreen
import com.sermilion.kmpcomposestarter.feature.home.ui.HomeScreen
import com.sermilion.kmpcomposestarter.feature.profile.ui.ProfileScreen

@Composable
fun StarterNavHost(
  isLoggedIn: Boolean,
  userComponent: UserDependencies?,
  authRepository: AuthRepository,
) {
  val config = remember {
    SavedStateConfiguration {
      serializersModule = appSerializersModule
    }
  }

  val startDestination = if (isLoggedIn) HomeRoute else LoginRoute

  key(isLoggedIn) {
    val backStack = rememberNavBackStack(config, startDestination)

    val screenComponentFactory: () -> com.sermilion.kmpcomposestarter.common.di.ScreenComponentProvider = remember(userComponent) {
      {
        object : com.sermilion.kmpcomposestarter.common.di.ScreenComponentProvider {
          override val diViewModelFactory: androidx.lifecycle.ViewModelProvider.Factory
            get() = userComponent?.screenComponentFactory?.create()?.diViewModelFactory
              ?: throw IllegalStateException("User not logged in")
        }
      }
    }

    CompositionLocalProvider(
      LocalScreenComponentFactory provides screenComponentFactory
    ) {
      NavDisplay(backStack = backStack) { entry ->
        when (entry.key) {
          is LoginRoute -> LoginScreen(
            authRepository = authRepository,
            onNavigateToRegister = { backStack.add(RegisterRoute) },
            onLoginSuccess = {
              backStack.removeAll { true }
              backStack.add(HomeRoute)
            }
          )
          is RegisterRoute -> RegisterScreen(
            authRepository = authRepository,
            onNavigateBack = { backStack.removeLastOrNull() },
            onRegisterSuccess = {
              backStack.removeAll { true }
              backStack.add(HomeRoute)
            }
          )
          is HomeRoute -> HomeScreen(
            onNavigateToProfile = { backStack.add(ProfileRoute) }
          )
          is ProfileRoute -> ProfileScreen(
            authRepository = authRepository,
            userComponent = userComponent,
            onNavigateBack = { backStack.removeLastOrNull() },
            onLogout = {
              backStack.removeAll { true }
              backStack.add(LoginRoute)
            }
          )
        }
      }
    }
  }
}
