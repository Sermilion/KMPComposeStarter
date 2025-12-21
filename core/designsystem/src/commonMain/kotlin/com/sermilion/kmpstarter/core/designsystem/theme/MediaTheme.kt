package com.sermilion.kmpstarter.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val colors =
  darkColorScheme(
    background = Color.Black,
    surface = Color.White.copy(alpha = 0.7f),
    primary = Green600,
    secondary = Grey600,
  )

@Composable
fun MediaTheme(content: @Composable () -> Unit) {
  CompositionLocalProvider(LocalKmpColors provides defaultMediaColors()) {
    MaterialTheme(
      colorScheme = colors,
      typography = KmpTypography,
      shapes = KmpShapes,
      content = content,
    )
  }
}
