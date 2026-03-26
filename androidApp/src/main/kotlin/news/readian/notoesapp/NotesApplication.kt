package news.readian.notoesapp

import android.app.Application

class NotesApplication : Application() {

  val component: AndroidApplicationComponent by lazy {
    createAndroidComponent(this)
  }

  override fun onCreate() {
    super.onCreate()
  }
}
