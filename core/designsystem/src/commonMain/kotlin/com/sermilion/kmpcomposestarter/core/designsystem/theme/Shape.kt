package com.sermilion.kmpcomposestarter.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val KmpShapes =
  Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    // Monotonic on purpose: `large` used to be 0.dp, i.e. sharper than `medium`.
    large = RoundedCornerShape(16.dp),
  )
