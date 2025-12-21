package com.sermilion.kmpstarter.core.designsystem.theme

import androidx.compose.runtime.Composable

object KmpContentAlpha {
  val highEmphasis: Float
    @Composable get() = contentAlpha(1.0f)

  val mediumEmphasis: Float
    @Composable get() = contentAlpha(0.83f)

  val lowEmphasis: Float
    @Composable get() = contentAlpha(0.46f)

  val veryLowEmphasis: Float
    @Composable get() = contentAlpha(0.10f)

  @Composable
  private fun contentAlpha(normalAlpha: Float, mediaThemeAlpha: Float = normalAlpha): Float {
    val mediaTheme = KmpTheme.kmpColors.isMediaTheme
    return if (mediaTheme) mediaThemeAlpha else normalAlpha
  }
}
