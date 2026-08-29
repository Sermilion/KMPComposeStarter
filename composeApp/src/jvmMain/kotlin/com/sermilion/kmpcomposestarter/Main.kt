package com.sermilion.kmpcomposestarter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
  // Desktop is the one target with no framework-owned process lifecycle: without this the
  // client's engine threads and connection pool outlive the window and keep the JVM alive.
  Runtime.getRuntime().addShutdownHook(
    Thread { JvmAppComponentHolder.component.httpClient.close() },
  )

  application {
    Window(
      onCloseRequest = ::exitApplication,
      title = "KMP Compose Starter",
    ) {
      StarterRoot(JvmAppComponentHolder.component)
    }
  }
}
