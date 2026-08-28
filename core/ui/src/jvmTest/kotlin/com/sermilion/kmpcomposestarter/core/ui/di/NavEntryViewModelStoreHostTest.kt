package com.sermilion.kmpcomposestarter.core.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private class TrackingViewModel : ViewModel() {
  var cleared = false
    private set

  override fun onCleared() {
    cleared = true
  }
}

class NavEntryViewModelStoreHostTest :
  FunSpec({

    fun trackedViewModel(host: NavEntryViewModelStoreHost, session: Any?) =
      ViewModelProvider.create(
        store = host.ownerFor(session).viewModelStore,
        factory = viewModelFactory { initializer { TrackingViewModel() } },
      )[TrackingViewModel::class]

    test("leaving a session drops the nav-entry ViewModels with it") {
      val host = NavEntryViewModelStoreHost()
      val session = Any()
      val owner = host.ownerFor(session)
      val entryViewModel = trackedViewModel(host, session)

      // The session ends and another one replaces it.
      val nextSession = Any()
      val nextOwner = host.ownerFor(nextSession)

      nextOwner shouldNotBe owner
      entryViewModel.cleared shouldBe true
      trackedViewModel(host, nextSession) shouldNotBe entryViewModel
    }

    test("signing in keeps the pre-auth entry stores, so the sign-in coroutine survives") {
      val host = NavEntryViewModelStoreHost()
      val preAuthOwner = host.ownerFor(null)
      val loginViewModel = trackedViewModel(host, null)

      host.ownerFor(Any()) shouldBe preAuthOwner
      loginViewModel.cleared shouldBe false
    }
  })
