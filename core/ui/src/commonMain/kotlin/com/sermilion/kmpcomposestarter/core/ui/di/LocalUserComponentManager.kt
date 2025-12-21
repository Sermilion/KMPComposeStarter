package com.sermilion.kmpcomposestarter.core.ui.di

import androidx.compose.runtime.staticCompositionLocalOf
import com.sermilion.kmpcomposestarter.core.domain.di.UserComponentManager

val LocalUserComponentManager = staticCompositionLocalOf<UserComponentManager> {
  error("UserComponentManager not provided")
}
