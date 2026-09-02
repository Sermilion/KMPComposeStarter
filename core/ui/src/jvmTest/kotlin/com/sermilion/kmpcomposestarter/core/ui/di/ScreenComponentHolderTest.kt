package com.sermilion.kmpcomposestarter.core.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sermilion.kmpcomposestarter.common.di.ScreenComponentProvider
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.reflect.KClass

private class FakeScreenComponent : ScreenComponentProvider {
  override val viewModelFactory: ViewModelProvider.Factory =
    object : ViewModelProvider.Factory {
      override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras,
      ): T = error("not used")
    }
}

class ScreenComponentHolderTest :
  FunSpec({

    test("one screen component per nav entry, dropped when the entry leaves the back stack") {
      var created = 0
      val store = ViewModelStore()
      val factory =
        viewModelFactory {
          initializer { ScreenComponentHolder(FakeScreenComponent().also { created++ }) }
        }

      fun holder() = ViewModelProvider.create(store, factory)[ScreenComponentHolder::class]

      val first = holder().provider
      val second = holder().provider

      second shouldBe first
      created shouldBe 1

      store.clear()

      val afterDisposal = holder().provider
      afterDisposal shouldNotBe first
      created shouldBe 2
    }
  })
