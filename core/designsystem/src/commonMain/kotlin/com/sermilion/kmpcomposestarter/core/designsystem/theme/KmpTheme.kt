package com.sermilion.kmpcomposestarter.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * The one theme every host renders through.
 *
 * @param dynamicColor opts into the platform's wallpaper-derived palette where one exists
 *   (Android 12+). It defaults to `false` so a fork sees this template's own scheme first;
 *   platforms without a dynamic palette ignore it.
 */
@Composable
fun StarterTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val dynamicScheme = if (dynamicColor) dynamicColorSchemeOrNull(darkTheme) else null
  val colorScheme = dynamicScheme
    ?: if (darkTheme) KmpDarkColorScheme else KmpLightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = KmpTypography,
    shapes = KmpShapes,
    content = content,
  )
}
