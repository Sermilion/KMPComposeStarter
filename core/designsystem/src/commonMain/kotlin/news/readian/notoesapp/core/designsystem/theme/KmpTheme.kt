package news.readian.notoesapp.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp

@Composable
fun KmpMaterialTheme(kmpColors: KmpColors = defaultKmpColors(), content: @Composable () -> Unit) {
  val darkTheme = isSystemInDarkTheme()
  val colorScheme = if (darkTheme) KmpDarkColorScheme else KmpLightColorScheme

  CompositionLocalProvider(LocalKmpColors provides kmpColors) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = KmpTypography,
      shapes = KmpShapes,
      content = content,
    )
  }
}

@Composable
fun StarterTheme(content: @Composable () -> Unit) {
  KmpMaterialTheme(content = content)
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

  val backgroundTheme: BackgroundTheme
    @Composable get() = BackgroundTheme(
      color = MaterialTheme.colorScheme.surface,
      tonalElevation = 2.dp,
    )

  val gradientColors: GradientColors
    @Composable get() = if (isSystemInDarkTheme()) GradientColors() else LightDefaultGradientColors

  val outlinedTextFieldColors: TextFieldColors
    @Composable get() = kmpOutlinedTextFieldColors()

  val textFieldColors: TextFieldColors
    @Composable get() = kmpTextFieldColors()
}

enum class KmpSystemBarsTheme {
  Light,
  Dark,
}
