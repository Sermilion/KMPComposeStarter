package com.sermilion.kmpcomposestarter.core.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * The brand seed. Both schemes below are tonal ramps of this one hue, so a fork rebrands the
 * template by regenerating from a new seed rather than by hand-editing forty roles.
 *
 * The seed itself is too light to carry white text (2.2:1 against `onPrimary`), so the light
 * scheme's `primary` is the same hue at a darker tone. Measured contrast ratios are recorded in
 * `docs/architecture/conventions.md`.
 */
val Green600 = Color(0xFF92B215)

/**
 * Every role the app renders is assigned explicitly. Leaving one unset falls back to the Material
 * baseline purple, which is how a half-filled scheme leaks non-brand colour into a screen nobody
 * previewed.
 */
internal val KmpLightColorScheme =
  lightColorScheme(
    primary = Color(0xFF53660C),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD4EE70),
    onPrimaryContainer = Color(0xFF181E03),
    inversePrimary = Color(0xFFAED519),
    secondary = Color(0xFF576426),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFB1C465),
    onSecondaryContainer = Color(0xFF1A1D0B),
    tertiary = Color(0xFF2C6B38),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFCCEAD2),
    onTertiaryContainer = Color(0xFF0D2011),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFFF9FAF9),
    onBackground = Color(0xFF1B1C19),
    surface = Color(0xFFF9FAF9),
    onSurface = Color(0xFF1B1C19),
    surfaceVariant = Color(0xFFE2E3DC),
    onSurfaceVariant = Color(0xFF454839),
    surfaceTint = Color(0xFF53660C),
    inverseSurface = Color(0xFF30312C),
    inverseOnSurface = Color(0xFFF0F1EF),
    outline = Color(0xFF747A5F),
    outlineVariant = Color(0xFFC5C8B8),
    scrim = Color(0xFF000000),
    surfaceBright = Color(0xFFF9FAF9),
    surfaceDim = Color(0xFFDADBD7),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF3F4F2),
    surfaceContainer = Color(0xFFEEEEEC),
    surfaceContainerHigh = Color(0xFFE8E9E6),
    surfaceContainerHighest = Color(0xFFE2E3DF),
    primaryFixed = Color(0xFFD4EE70),
    primaryFixedDim = Color(0xFFAED519),
    onPrimaryFixed = Color(0xFF181E03),
    onPrimaryFixedVariant = Color(0xFF3E4C09),
    secondaryFixed = Color(0xFFDEE6BE),
    secondaryFixedDim = Color(0xFFBECE7F),
    onSecondaryFixed = Color(0xFF1A1D0B),
    onSecondaryFixedVariant = Color(0xFF414A1C),
    tertiaryFixed = Color(0xFFCCEAD2),
    tertiaryFixedDim = Color(0xFF97D4A3),
    onTertiaryFixed = Color(0xFF0D2011),
    onTertiaryFixedVariant = Color(0xFF21502B),
  )

internal val KmpDarkColorScheme =
  darkColorScheme(
    primary = Color(0xFFAED519),
    onPrimary = Color(0xFF2B3406),
    primaryContainer = Color(0xFF3E4C09),
    onPrimaryContainer = Color(0xFFD4EE70),
    inversePrimary = Color(0xFF53660C),
    secondary = Color(0xFFBECE7F),
    onSecondary = Color(0xFF2D3314),
    secondaryContainer = Color(0xFF414A1C),
    onSecondaryContainer = Color(0xFFDEE6BE),
    tertiary = Color(0xFF97D4A3),
    onTertiary = Color(0xFF16361D),
    tertiaryContainer = Color(0xFF21502B),
    onTertiaryContainer = Color(0xFFCCEAD2),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),
    background = Color(0xFF131312),
    onBackground = Color(0xFFE2E3DF),
    surface = Color(0xFF131312),
    onSurface = Color(0xFFE2E3DF),
    surfaceVariant = Color(0xFF454839),
    onSurfaceVariant = Color(0xFFC5C8B8),
    surfaceTint = Color(0xFFAED519),
    inverseSurface = Color(0xFFE2E3DF),
    inverseOnSurface = Color(0xFF30312C),
    outline = Color(0xFF8E9476),
    outlineVariant = Color(0xFF454839),
    scrim = Color(0xFF000000),
    surfaceBright = Color(0xFF383934),
    surfaceDim = Color(0xFF131312),
    surfaceContainerLowest = Color(0xFF0E0F0D),
    surfaceContainerLow = Color(0xFF1B1C19),
    surfaceContainer = Color(0xFF1F201C),
    surfaceContainerHigh = Color(0xFF2A2A26),
    surfaceContainerHighest = Color(0xFF343530),
    primaryFixed = Color(0xFFD4EE70),
    primaryFixedDim = Color(0xFFAED519),
    onPrimaryFixed = Color(0xFF181E03),
    onPrimaryFixedVariant = Color(0xFF3E4C09),
    secondaryFixed = Color(0xFFDEE6BE),
    secondaryFixedDim = Color(0xFFBECE7F),
    onSecondaryFixed = Color(0xFF1A1D0B),
    onSecondaryFixedVariant = Color(0xFF414A1C),
    tertiaryFixed = Color(0xFFCCEAD2),
    tertiaryFixedDim = Color(0xFF97D4A3),
    onTertiaryFixed = Color(0xFF0D2011),
    onTertiaryFixedVariant = Color(0xFF21502B),
  )
