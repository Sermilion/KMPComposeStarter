package news.readian.notoesapp.core.designsystem.theme

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

val ReadianBlue = Color(0xFF36A0DA)
internal val ReadianBlueDisabled = Color(0x8036A0DA)
internal val ReadianBlueDark = Color(0xFF0E173F)
internal val Light1 = Color(0xFFFFFFFF)
internal val Light2 = Color(0xFFF2F2F2)
internal val Light3 = Color(0xFFF6F7F8)
internal val Light4 = Color(0xFFE9EAEA)
internal val Light5 = Color(0xFFC1C1C1)
internal val Light6 = Color(0xFFC7C9CC)
internal val Light7 = Color(0xFFA9ACB2)
internal val Light8 = Color(0xFF92959B)
internal val Dark1 = Color(0xFF000000)
internal val Dark2 = Color(0xFF010101)
internal val Dark3 = Color(0xFF1A1C1F)
internal val Dark4 = Color(0xFF222222)
internal val Dark5 = Color(0xFF383A3E)
internal val Dark6 = Color(0x995B5D63)
internal val Dark7 = Color(0xFFA9ACB2)
internal val Positive = Color(0xFF3AD26D)
internal val Negative = Color(0xFFFF585B)
internal val Red = Color(0xFFEA4336)
internal val Blue95 = Color(0xFFDCF5FF)
internal val DarkGreenGray95 = Color(0xFFF0F1EC)
internal val DarkPurpleGray95 = Color(0xFFFAEEEF)
internal val Orange95 = Color(0xFFFFEDE6)
internal val Purple95 = Color(0xFFFFEBFB)

object ReadianColors {
  val RoyalBlue = Color(0xFF1C6EF2)
  val Vermilion = Color(0xFFE22518)
  val Amber = Color(0xFFFABD05)
  val ForestGreen = Color(0xFF298541)
  val PumpkinOrange = Color(0xFFF56A00)
  val OceanBlue = Color(0xFF2A8289)
  val PureBlue = Color(0xFF0000FF)
  val ElectricPurple = Color(0xFF9900FF)
  val ReadingColor = Color(0xFFFFFFFF)

  val allReadianColors = listOf(
    RoyalBlue,
    Vermilion,
    Amber,
    ForestGreen,
    PumpkinOrange,
    OceanBlue,
    PureBlue,
    ElectricPurple,
    Positive,
  )
}

internal val KmpLightColorScheme = lightColorScheme(
  primary = ReadianBlue,
  onPrimary = Light1,
  primaryContainer = ReadianBlue,
  onPrimaryContainer = Light1,
  secondary = Dark7,
  onSecondary = ReadianBlue,
  secondaryContainer = Light4,
  onSecondaryContainer = Dark1,
  tertiary = Dark2,
  onTertiary = Light8,
  tertiaryContainer = Dark2,
  onTertiaryContainer = Light1,
  error = Red,
  onError = Light1,
  errorContainer = Dark2,
  onErrorContainer = Negative,
  background = Light1,
  onBackground = Dark1,
  surface = Light1,
  onSurface = Dark1,
  surfaceVariant = Light3,
  onSurfaceVariant = Light8,
  outline = Light8,
  surfaceTint = Light1,
  surfaceContainer = Light1,
  surfaceContainerLow = Light1,
  surfaceContainerLowest = Light1,
  surfaceContainerHigh = Light1,
  surfaceContainerHighest = Light1,
)

internal val KmpDarkColorScheme = darkColorScheme(
  primary = Light4,
  onPrimary = Dark2,
  primaryContainer = ReadianBlue,
  onPrimaryContainer = Light1,
  secondary = Dark7,
  onSecondary = ReadianBlue,
  secondaryContainer = Dark3,
  onSecondaryContainer = Light8,
  tertiary = Light4,
  onTertiary = Dark1,
  tertiaryContainer = Dark2,
  onTertiaryContainer = Light7,
  error = Red,
  onError = Light4,
  errorContainer = Dark2,
  onErrorContainer = Light4,
  background = Dark2,
  onBackground = Light4,
  surface = Dark3,
  onSurface = Light1,
  surfaceVariant = Dark3,
  onSurfaceVariant = Dark7,
  outline = Light7,
  surfaceTint = Dark2,
  surfaceContainer = Dark2,
  surfaceContainerLow = Dark2,
  surfaceContainerLowest = Dark2,
  surfaceContainerHigh = Dark2,
  surfaceContainerHighest = Dark2,
)

val LightDefaultGradientColors = GradientColors(
  primary = Purple95,
  secondary = Orange95,
  tertiary = Blue95,
  neutral = DarkPurpleGray95,
)

@Composable
internal fun kmpOutlinedTextFieldColors(): TextFieldColors {
  val placeholderColor = Dark5.copy(alpha = KmpContentAlpha.lowEmphasis)
  return OutlinedTextFieldDefaults.colors(
    focusedTextColor = KmpTheme.colors.onSurface,
    unfocusedTextColor = KmpTheme.colors.onSurface,
    focusedContainerColor = KmpTheme.colors.surface,
    unfocusedContainerColor = KmpTheme.colors.surface,
    focusedPlaceholderColor = placeholderColor,
    unfocusedPlaceholderColor = placeholderColor,
    disabledPlaceholderColor = placeholderColor,
    focusedBorderColor = KmpTheme.colors.primary,
    unfocusedBorderColor = Light6,
    cursorColor = KmpTheme.colors.primary,
  )
}

@Composable
internal fun kmpTextFieldColors(): TextFieldColors {
  val placeholderColor = Dark5.copy(alpha = KmpContentAlpha.lowEmphasis)
  return TextFieldDefaults.colors(
    focusedPlaceholderColor = placeholderColor,
    unfocusedPlaceholderColor = placeholderColor,
    disabledPlaceholderColor = placeholderColor,
    focusedIndicatorColor = KmpTheme.colors.primary,
    unfocusedIndicatorColor = Light6,
    errorContainerColor = KmpTheme.colors.surface,
    errorIndicatorColor = KmpTheme.colors.error,
    cursorColor = KmpTheme.colors.primary,
    focusedTextColor = KmpTheme.colors.onSurface,
    unfocusedTextColor = KmpTheme.colors.onSurface,
    focusedContainerColor = KmpTheme.colors.surface,
    unfocusedContainerColor = KmpTheme.colors.surface,
  )
}

fun defaultKmpColors(
  backgroundVariant: Color = Light3,
  surfaceVariant: Color = Light2,
  onSurface: Color = Dark1,
  outlineNormal: Color = Light5,
  outlineDisabled: Color = Light6,
  onSurfaceInverted: Color = Light1,
  separator: Color = Light4,
  scrim: Color = Dark1.copy(alpha = 0.38f),
  positive: Color = Positive,
  warning: Color = ReadianColors.Amber,
  statusAttention: Color = Negative,
  info: Color = ReadianColors.RoyalBlue,
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
  backgroundVariant: Color = Dark3,
  surfaceVariant: Color = Dark4,
  onSurface: Color = Light1,
  outlineNormal: Color = Dark7,
  outlineDisabled: Color = Dark6,
  onSurfaceInverted: Color = Light1,
  separator: Color = Dark5,
  scrim: Color = Dark1.copy(alpha = 0.54f),
  positive: Color = Positive,
  warning: Color = ReadianColors.Amber,
  statusAttention: Color = Negative,
  info: Color = ReadianColors.RoyalBlue,
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
