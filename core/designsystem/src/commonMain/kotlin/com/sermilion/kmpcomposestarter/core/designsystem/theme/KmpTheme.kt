package com.sermilion.kmpcomposestarter.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun StarterTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colorScheme = KmpLightColorScheme,
    typography = KmpTypography,
    shapes = KmpShapes,
    content = content,
  )
}
