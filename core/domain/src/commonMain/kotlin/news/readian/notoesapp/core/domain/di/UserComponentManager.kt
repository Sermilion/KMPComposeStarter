package news.readian.notoesapp.core.domain.di

import kotlinx.coroutines.flow.StateFlow
import news.readian.notoesapp.core.domain.model.UserData

interface UserComponentManager {
  val userComponent: UserDependencies?
  val userComponentFlow: StateFlow<UserDependencies?>

  fun createComponent(userData: UserData)
  fun destroyComponent()
}
