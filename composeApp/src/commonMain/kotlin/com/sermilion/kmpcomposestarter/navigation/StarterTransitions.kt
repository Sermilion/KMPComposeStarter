package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object StarterTransitions {
  private const val DURATION_FAST = 200
  private const val DURATION_NORMAL = 300

  val fadeEnter: EnterTransition = fadeIn(animationSpec = tween(DURATION_FAST))
  val fadeExit: ExitTransition = fadeOut(animationSpec = tween(DURATION_FAST))

  val slideInFromRight: EnterTransition = slideInHorizontally(
    animationSpec = tween(DURATION_NORMAL),
    initialOffsetX = { fullWidth -> fullWidth },
  )

  val slideOutToRight: ExitTransition = slideOutHorizontally(
    animationSpec = tween(DURATION_NORMAL),
    targetOffsetX = { fullWidth -> fullWidth },
  )

  val slideInFromLeft: EnterTransition = slideInHorizontally(
    animationSpec = tween(DURATION_NORMAL),
    initialOffsetX = { fullWidth -> -fullWidth },
  )

  val slideOutToLeft: ExitTransition = slideOutHorizontally(
    animationSpec = tween(DURATION_NORMAL),
    targetOffsetX = { fullWidth -> -fullWidth },
  )
}
