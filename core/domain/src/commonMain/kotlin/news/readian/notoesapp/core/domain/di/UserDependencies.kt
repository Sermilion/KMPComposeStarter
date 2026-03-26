package news.readian.notoesapp.core.domain.di

import news.readian.notoesapp.core.domain.model.UserData

interface UserDependencies {
  val userData: UserData
  val screenComponentFactory: ScreenComponent.Factory
}
