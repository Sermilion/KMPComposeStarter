package com.sermilion.kmpstarter.core.designsystem.theme

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

val Grey50 = Color(0xFFF8F8F8)
val Grey100 = Color(0xFFEAEAEA)
val Grey200 = Color(0xFFD5D5D5)
val Grey300 = Color(0xFF969696)
val Grey400 = Color(0xFF414141)
val Grey500 = Color(0xFF2C2C2C)
val Grey600 = Color(0xFF1A1A1A)
val Green500 = Color(0xFFA2C617)
val Green600 = Color(0xFF92B215)
val WarningRed500 = Color(0xFFD62D2D)
val WarningOrange = Color(0xFFFF8C00)
val InfoBlue = Color(0xFF356EFF)

internal val KmpLightColorScheme =
  lightColorScheme(
    primary = Green600,
    onSurface = Grey600,
    tertiary = Green600,
    onBackground = Grey600,
    background = Color.White,
    surface = Color.White,
    surfaceTint = Color.White,
    surfaceContainer = Color.White,
    surfaceContainerLow = Color.White,
    surfaceContainerLowest = Color.White,
    surfaceVariant = Color.White,
    surfaceContainerHigh = Color.White,
    surfaceContainerHighest = Color.White,
    secondaryContainer = Color.White,
  )

@Composable
internal fun kmpOutlinedTextFieldColors(): TextFieldColors {
  val placeholderColor = Grey600.copy(alpha = KmpContentAlpha.lowEmphasis)
  return OutlinedTextFieldDefaults.colors(
    focusedPlaceholderColor = placeholderColor,
    unfocusedPlaceholderColor = placeholderColor,
    disabledPlaceholderColor = placeholderColor,
  )
}

@Composable
internal fun kmpTextFieldColors(): TextFieldColors {
  val placeholderColor = Grey600.copy(alpha = KmpContentAlpha.lowEmphasis)
  return TextFieldDefaults.colors(
    focusedPlaceholderColor = placeholderColor,
    unfocusedPlaceholderColor = placeholderColor,
    disabledPlaceholderColor = placeholderColor,
    focusedIndicatorColor = KmpTheme.colors.background,
    unfocusedIndicatorColor = KmpTheme.colors.background,
    errorContainerColor = KmpTheme.colors.background,
    errorIndicatorColor = KmpTheme.colors.background,
    cursorColor = KmpTheme.colors.primary,
    focusedTextColor = KmpTheme.colors.onSurface.copy(KmpContentAlpha.highEmphasis),
    unfocusedTextColor = KmpTheme.colors.onSurface.copy(KmpContentAlpha.highEmphasis),
    focusedContainerColor = KmpTheme.colors.surface,
    unfocusedContainerColor = KmpTheme.colors.surface,
  )
}

fun defaultKmpColors(
  backgroundVariant: Color = Grey100,
  surfaceVariant: Color = Grey50,
  onSurface: Color = Grey400,
  outlineNormal: Color = Grey300,
  outlineDisabled: Color = Grey200,
  onSurfaceInverted: Color = Color.White,
  separator: Color = Grey100,
  scrim: Color = Grey500.copy(alpha = 0.38f),
  positive: Color = Green600,
  warning: Color = WarningOrange,
  statusAttention: Color = WarningRed500,
  info: Color = InfoBlue,
): KmpColors = createKmpColors(
  backgroundVariant = backgroundVariant,
  surfaceVariant = surfaceVariant,
  onSurface = onSurface,
  outlineNormal = outlineNormal,
  outlineDisabled = outlineDisabled,
  onSurfaceInverted = onSurfaceInverted,
  separator = separator,
  scrim = scrim,
  positive = positive,
  warning = warning,
  statusAttention = statusAttention,
  info = info,
  isMediaTheme = false,
)

fun defaultMediaColors(
  backgroundVariant: Color = Grey100,
  surfaceVariant: Color = Grey50,
  onSurface: Color = Grey400,
  outlineNormal: Color = Grey300,
  outlineDisabled: Color = Grey200,
  onSurfaceInverted: Color = Color.White,
  separator: Color = Grey100,
  scrim: Color = Grey500.copy(alpha = 0.38f),
  positive: Color = Green600,
  warning: Color = WarningOrange,
  statusAttention: Color = WarningRed500,
  info: Color = InfoBlue,
): KmpColors = createKmpColors(
  backgroundVariant = backgroundVariant,
  surfaceVariant = surfaceVariant,
  onSurface = onSurface,
  outlineNormal = outlineNormal,
  outlineDisabled = outlineDisabled,
  onSurfaceInverted = onSurfaceInverted,
  separator = separator,
  scrim = scrim,
  positive = positive,
  warning = warning,
  statusAttention = statusAttention,
  info = info,
  isMediaTheme = true,
)

@Suppress("LongParameterList")
private fun createKmpColors(
  backgroundVariant: Color,
  surfaceVariant: Color,
  onSurface: Color,
  outlineNormal: Color,
  outlineDisabled: Color,
  onSurfaceInverted: Color,
  separator: Color,
  scrim: Color,
  positive: Color,
  warning: Color,
  statusAttention: Color,
  info: Color,
  isMediaTheme: Boolean,
): KmpColors = KmpColors(
  backgroundVariant = backgroundVariant,
  surfaceVariant = surfaceVariant,
  onSurface = onSurface,
  outlineNormal = outlineNormal,
  outlineDisabled = outlineDisabled,
  onSurfaceInverted = onSurfaceInverted,
  separator = separator,
  positive = positive,
  warning = warning,
  scrim = scrim,
  statusAttention = statusAttention,
  info = info,
  isMediaTheme = isMediaTheme,
)

@Suppress("LongParameterList")
class KmpColors(
  backgroundVariant: Color,
  surfaceVariant: Color,
  onSurface: Color,
  outlineNormal: Color,
  outlineDisabled: Color,
  onSurfaceInverted: Color,
  separator: Color,
  positive: Color,
  warning: Color,
  info: Color,
  scrim: Color,
  statusAttention: Color,
  isMediaTheme: Boolean,
) {
  var backgroundVariant by mutableStateOf(backgroundVariant, structuralEqualityPolicy())
    internal set

  var surfaceVariant by mutableStateOf(surfaceVariant, structuralEqualityPolicy())
    internal set

  var onSurface by mutableStateOf(onSurface, structuralEqualityPolicy())
    internal set

  var outlineNormal by mutableStateOf(outlineNormal, structuralEqualityPolicy())
    internal set

  var outlineDisabled by mutableStateOf(outlineDisabled, structuralEqualityPolicy())
    internal set

  var onSurfaceInverted by mutableStateOf(onSurfaceInverted, structuralEqualityPolicy())
    internal set

  var separator by mutableStateOf(separator, structuralEqualityPolicy())
    internal set

  var positive by mutableStateOf(positive, structuralEqualityPolicy())
    internal set

  var warning by mutableStateOf(warning, structuralEqualityPolicy())
    internal set

  var scrim by mutableStateOf(scrim, structuralEqualityPolicy())
    internal set

  var isMediaTheme by mutableStateOf(isMediaTheme, structuralEqualityPolicy())
    internal set

  var statusAttention by mutableStateOf(statusAttention, structuralEqualityPolicy())
    internal set

  var info by mutableStateOf(info, structuralEqualityPolicy())
    internal set

  fun copy(
    backgroundVariant: Color = this.backgroundVariant,
    surfaceVariant: Color = this.surfaceVariant,
    onSurface: Color = this.onSurface,
    outlineNormal: Color = this.outlineNormal,
    outlineDisabled: Color = this.outlineDisabled,
    onSurfaceInverted: Color = this.onSurfaceInverted,
    separator: Color = this.separator,
    positive: Color = this.positive,
    warning: Color = this.warning,
    scrim: Color = this.scrim,
    statusAttention: Color = this.statusAttention,
    isMediaTheme: Boolean = this.isMediaTheme,
    info: Color = this.info,
  ): KmpColors = KmpColors(
    backgroundVariant = backgroundVariant,
    surfaceVariant = surfaceVariant,
    onSurface = onSurface,
    outlineNormal = outlineNormal,
    outlineDisabled = outlineDisabled,
    onSurfaceInverted = onSurfaceInverted,
    separator = separator,
    positive = positive,
    warning = warning,
    scrim = scrim,
    statusAttention = statusAttention,
    isMediaTheme = isMediaTheme,
    info = info,
  )

  override fun toString(): String = "KmpColors(" +
    "backgroundVariant=$backgroundVariant, " +
    "outlineNormal=$outlineNormal, " +
    "outlineDisabled=$outlineDisabled, " +
    "surfaceVariant=$surfaceVariant, " +
    "onSurface=$onSurface, " +
    "onSurfaceInverted=$onSurfaceInverted, " +
    "separator=$separator, " +
    "positive=$positive, " +
    "warning=$warning, " +
    "info=$info, " +
    "scrim=$scrim, " +
    "statusAttention=$statusAttention, " +
    "isMediaTheme=$isMediaTheme, " +
    ")"
}

internal val LocalKmpColors = staticCompositionLocalOf { defaultKmpColors() }
