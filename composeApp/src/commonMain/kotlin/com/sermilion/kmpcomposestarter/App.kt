package com.sermilion.kmpcomposestarter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.sermilion.kmpcomposestarter.core.data.repository.AuthRepository
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager
import com.sermilion.kmpcomposestarter.navigation.StarterNavHost

@Composable
fun App(
  userComponentManager: UserComponentManager,
  authRepository: AuthRepository,
) {
  val userComponent by userComponentManager.userComponentFlow.collectAsState()
  val isLoggedIn = userComponent != null

  Surface(modifier = Modifier.fillMaxSize()) {
    StarterNavHost(
      isLoggedIn = isLoggedIn,
      userComponent = userComponent,
      authRepository = authRepository,
    )
  }
}
