package news.readian.notoesapp.core.designsystem.theme

import androidx.compose.runtime.Composable

object KmpContentAlpha {
  val highEmphasis: Float
    @Composable get() = contentAlpha(1.0f)

  val mediumEmphasis: Float
    @Composable get() = contentAlpha(0.6f)

  val lowEmphasis: Float
    @Composable get() = contentAlpha(0.38f)

  val veryLowEmphasis: Float
    @Composable get() = contentAlpha(0.1f)

  @Composable
  private fun contentAlpha(normalAlpha: Float, mediaThemeAlpha: Float = normalAlpha): Float {
    val mediaTheme = KmpTheme.kmpColors.isMediaTheme
    return if (mediaTheme) mediaThemeAlpha else normalAlpha
  }
}

object ReadianContentAlpha {
  val highEmphasis: Float
    @Composable get() = KmpContentAlpha.highEmphasis

  val mediumEmphasis: Float
    @Composable get() = KmpContentAlpha.mediumEmphasis

  val lowEmphasis: Float
    @Composable get() = KmpContentAlpha.lowEmphasis
}
