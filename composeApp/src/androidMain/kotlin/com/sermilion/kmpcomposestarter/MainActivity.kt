package com.sermilion.kmpcomposestarter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.sermilion.kmpcomposestarter.common.di.LocalViewModelFactory
import com.sermilion.kmpcomposestarter.common.di.LocalViewModelProvider
import com.sermilion.kmpcomposestarter.core.designsystem.theme.StarterTheme

class MainActivity : ComponentActivity() {

  private val component: AndroidApplicationComponent by lazy {
    (application as StarterApplication).component
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      CompositionLocalProvider(
        LocalViewModelFactory provides component.viewModelFactory,
        LocalViewModelProvider provides component,
      ) {
        StarterTheme {
          App(
            userComponentManager = component.userComponentManager,
            authRepository = component.authRepository,
          )
        }
      }
    }
  }
}
