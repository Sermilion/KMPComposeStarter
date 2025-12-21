package com.sermilion.kmpstarter.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun KmpMaterialTheme(kmpColors: KmpColors = defaultKmpColors(), content: @Composable () -> Unit) {
  CompositionLocalProvider(LocalKmpColors provides kmpColors) {
    MaterialTheme(
      colorScheme = KmpLightColorScheme,
      typography = KmpTypography,
      shapes = KmpShapes,
      content = content,
    )
  }
}

object KmpTheme {
  val colors: ColorScheme
    @Composable get() = MaterialTheme.colorScheme

  val kmpColors: KmpColors
    @Composable get() = LocalKmpColors.current

  val typography: Typography
    @Composable get() = MaterialTheme.typography

  val shapes: Shapes
    @Composable get() = MaterialTheme.shapes

  val outlinedTextFieldColors: TextFieldColors
    @Composable get() = kmpOutlinedTextFieldColors()

  val textFieldColors: TextFieldColors
    @Composable get() = kmpTextFieldColors()
}

enum class KmpSystemBarsTheme {
  Light,
  Dark,
}
