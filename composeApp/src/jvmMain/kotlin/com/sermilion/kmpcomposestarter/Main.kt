package com.sermilion.kmpcomposestarter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kmpcomposestarter.composeapp.generated.resources.Res
import kmpcomposestarter.composeapp.generated.resources.app_window_title
import org.jetbrains.compose.resources.stringResource

fun main() {
  Runtime.getRuntime().addShutdownHook(
    Thread { JvmAppComponentHolder.component.httpClient.close() },
  )

  application {
    Window(
      onCloseRequest = ::exitApplication,
      title = stringResource(Res.string.app_window_title),
    ) {
      StarterRoot(JvmAppComponentHolder.component)
    }
  }
}
