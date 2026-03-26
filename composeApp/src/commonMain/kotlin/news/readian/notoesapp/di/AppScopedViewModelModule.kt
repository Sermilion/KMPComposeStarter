
package news.readian.notoesapp.di

import androidx.lifecycle.ViewModel
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import news.readian.notoesapp.common.di.AssistedArgs
import news.readian.notoesapp.common.di.ViewModelEntry
import news.readian.notoesapp.feature.auth.viewmodel.LoginViewModel
import news.readian.notoesapp.feature.auth.viewmodel.RegisterViewModel
import news.readian.notoesapp.feature.onboarding.tutorial.TutorialViewModel
import news.readian.notoesapp.feature.onboarding.welcome.WelcomeViewModel
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import kotlin.reflect.KClass

@ContributesTo(AppScope::class)
interface AppScopedViewModelModule {

  @Provides
  @IntoSet
  fun provideTutorialViewModelEntry(factory: () -> TutorialViewModel): ViewModelEntry =
    object : ViewModelEntry {
      override val kclass: KClass<out ViewModel> = TutorialViewModel::class
      override fun create(args: AssistedArgs): ViewModel = factory()
    }

  @Provides
  @IntoSet
  fun provideWelcomeViewModelEntry(factory: () -> WelcomeViewModel): ViewModelEntry =
    object : ViewModelEntry {
      override val kclass: KClass<out ViewModel> = WelcomeViewModel::class
      override fun create(args: AssistedArgs): ViewModel = factory()
    }

  @Provides
  @IntoSet
  fun provideLoginViewModelEntry(factory: () -> LoginViewModel): ViewModelEntry =
    object : ViewModelEntry {
      override val kclass: KClass<out ViewModel> = LoginViewModel::class
      override fun create(args: AssistedArgs): ViewModel = factory()
    }

  @Provides
  @IntoSet
  fun provideRegisterViewModelEntry(factory: () -> RegisterViewModel): ViewModelEntry =
    object : ViewModelEntry {
      override val kclass: KClass<out ViewModel> = RegisterViewModel::class
      override fun create(args: AssistedArgs): ViewModel = factory()
    }
}
