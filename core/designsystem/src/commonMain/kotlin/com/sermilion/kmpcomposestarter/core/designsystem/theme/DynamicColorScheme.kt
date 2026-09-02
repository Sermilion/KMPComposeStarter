package com.sermilion.kmpcomposestarter.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

/**
 * The platform's own colour scheme, or `null` where the platform has none.
 *
 * Only Android 12+ answers this; every other target returns `null` and [StarterTheme] falls back
 * to the template scheme.
 */
@Composable
expect fun dynamicColorSchemeOrNull(darkTheme: Boolean): ColorScheme?
