package com.sermilion.kmpcomposestarter

import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.LoginViewModel
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailViewModel
import com.sermilion.kmpcomposestarter.feature.home.viewmodel.HomeViewModel
import com.sermilion.kmpcomposestarter.feature.profile.viewmodel.ProfileViewModel
import com.sermilion.kmpcomposestarter.feature.settings.viewmodel.SettingsViewModel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldNotContain
import kotlinx.coroutines.test.runTest

/**
 * Runs against the merged graph rather than a fake, because the defect it pins was in the merge.
 *
 * `AppScope` and `ScreenScope` each provide a ViewModel factory. While both were the same type,
 * kotlin-inject matched the app component's accessor — visible to every descendant — instead of the
 * screen component's own provider, so the screen factory held only the pre-auth ViewModels and
 * every screen crashed on first navigation with "is not registered". The two factory types keep
 * them apart now; a screen factory that reverts to the app scope's registrations fails here.
 *
 * The screen factory sees the pre-auth ViewModels as well, because multibindings accumulate down
 * the scope chain. Only the reverse — the app scope seeing a screen ViewModel — is a defect.
 */
class ScreenScopeViewModelRegistrationTest :
  FunSpec({

    test("the screen factory registers the screen ViewModels the app factory cannot see") {
      runTest {
        val component = JvmAppComponentHolder.component
        val manager = component.userComponentManager
        val session =
          manager.createComponent(
            UserData(id = "screen-scope-test", email = "test@example.com", name = "Test"),
          )

        try {
          val screenViewModels =
            session.screenComponentFactory
              .create()
              .viewModelFactory
              .registeredViewModels

          screenViewModels shouldContainAll
            setOf(
              HomeViewModel::class,
              DetailViewModel::class,
              ProfileViewModel::class,
              SettingsViewModel::class,
            )
          val preAuthViewModels = component.viewModelFactory.registeredViewModels
          preAuthViewModels shouldContainAll setOf(LoginViewModel::class)
          preAuthViewModels shouldNotContain HomeViewModel::class
        } finally {
          manager.destroyComponent()
        }
      }
    }
  })
