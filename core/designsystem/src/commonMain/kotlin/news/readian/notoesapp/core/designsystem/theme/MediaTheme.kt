package news.readian.notoesapp.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun MediaTheme(content: @Composable () -> Unit) {
  CompositionLocalProvider(LocalKmpColors provides defaultMediaColors()) {
    MaterialTheme(
      colorScheme = KmpDarkColorScheme,
      typography = KmpTypography,
      shapes = KmpShapes,
      content = content,
    )
  }
}
