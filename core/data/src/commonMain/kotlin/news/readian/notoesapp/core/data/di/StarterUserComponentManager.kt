package news.readian.notoesapp.core.data.di

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tatarka.inject.annotations.Inject
import news.readian.notoesapp.core.domain.di.UserComponentManager
import news.readian.notoesapp.core.domain.di.UserDependencies
import news.readian.notoesapp.core.domain.model.UserData
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class StarterUserComponentManager(private val userComponentFactory: UserComponent.Factory) :
  UserComponentManager {

  private val _userComponent = atomic<UserComponent?>(null)
  private val _userComponentFlow = MutableStateFlow<UserDependencies?>(null)

  override val userComponent: UserDependencies?
    get() = _userComponent.value

  override val userComponentFlow: StateFlow<UserDependencies?>
    get() = _userComponentFlow.asStateFlow()

  override fun createComponent(userData: UserData) {
    val current = _userComponent.value
    if (current == null) {
      val component = userComponentFactory.create(userData)
      _userComponent.value = component
      _userComponentFlow.value = component
    }
  }

  override fun destroyComponent() {
    _userComponent.value = null
    _userComponentFlow.value = null
  }
}
