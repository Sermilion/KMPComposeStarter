package com.sermilion.kmpcomposestarter

import androidx.compose.ui.window.ComposeUIViewController

@Suppress("FunctionName")
fun MainViewController() =
  ComposeUIViewController {
    StarterRoot(iosAppComponent)
  }
