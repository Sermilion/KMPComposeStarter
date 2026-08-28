package com.sermilion.kmpcomposestarter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
  Window(
    onCloseRequest = ::exitApplication,
    title = "KMP Compose Starter",
  ) {
    StarterRoot(JvmAppComponentHolder.component)
  }
}
