package com.sermilion.kmpcomposestarter

import android.app.Application

class StarterApplication : Application() {
  /** Process-level graph, created once and outside composition. */
  val component: AndroidApplicationComponent by lazy {
    createAndroidComponent(this)
  }
}
