package news.readian.notoesapp.core.data.di

import news.readian.notoesapp.common.di.UserScope
import news.readian.notoesapp.core.domain.di.ScreenComponent
import news.readian.notoesapp.core.domain.di.UserDependencies
import news.readian.notoesapp.core.domain.model.UserData
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesSubcomponent(UserScope::class)
@SingleIn(UserScope::class)
interface UserComponent : UserDependencies {

  override val userData: UserData
  override val screenComponentFactory: ScreenComponent.Factory

  @ContributesSubcomponent.Factory(AppScope::class)
  interface Factory {
    fun create(userData: UserData): UserComponent
  }
}
