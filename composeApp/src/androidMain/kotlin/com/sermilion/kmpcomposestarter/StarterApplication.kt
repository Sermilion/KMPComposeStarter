package com.sermilion.kmpcomposestarter

import android.app.Application

class StarterApplication : Application() {

  val component: AndroidApplicationComponent by lazy {
    createAndroidComponent(this)
  }

  override fun onCreate() {
    super.onCreate()
  }
}
