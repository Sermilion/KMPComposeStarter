package news.readian.notoesapp

import androidx.lifecycle.ViewModel
import news.readian.notoesapp.common.di.StarterViewModelFactory
import news.readian.notoesapp.common.di.ViewModelFactory
import news.readian.notoesapp.common.di.ViewModelProvider
import news.readian.notoesapp.core.data.di.UserComponent
import news.readian.notoesapp.core.data.repository.AuthRepository
import news.readian.notoesapp.core.domain.di.UserComponentManager
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.reflect.KClass

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class JvmApplicationComponent : ViewModelProvider {

  abstract val userComponentFactory: UserComponent.Factory
  abstract val userComponentManager: UserComponentManager
  abstract val authRepository: AuthRepository

  val viewModelFactory: ViewModelFactory
    get() = viewModelFactoryImpl

  abstract val viewModelFactoryImpl: StarterViewModelFactory

  override fun <T : ViewModel> provide(viewModelClass: KClass<T>): T =
    viewModelFactory.create(viewModelClass)
}

fun createJvmComponent(): JvmApplicationComponent = JvmApplicationComponent::class.create()
