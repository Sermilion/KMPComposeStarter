package news.readian.notoesapp.core.domain.di

import news.readian.notoesapp.common.di.ScreenScope
import news.readian.notoesapp.common.di.UserScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesSubcomponent(ScreenScope::class)
@SingleIn(ScreenScope::class)
interface ScreenComponent {

  val diViewModelFactory: DIViewModelFactory

  @ContributesSubcomponent.Factory(UserScope::class)
  interface Factory {
    fun create(): ScreenComponent
  }
}
