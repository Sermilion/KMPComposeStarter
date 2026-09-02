package com.sermilion.kmpcomposestarter.common.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.reflect.KClass

private class SampleViewModel(
  val label: String = "",
) : ViewModel()

private class OtherViewModel : ViewModel()

private class SampleEntry(
  private val build: (AssistedArgs) -> ViewModel,
) : ViewModelEntry {
  override val kclass: KClass<out ViewModel> = SampleViewModel::class
  override val savedStateHandleArgName: String? = null

  override fun create(args: AssistedArgs): ViewModel = build(args)
}

class StarterViewModelFactoryTest :
  FunSpec({

    test("a duplicate registration fails loudly and names the shadowed ViewModel") {
      val failure =
        shouldThrowAny {
          ScreenViewModelFactory(
            setOf(
              SampleEntry { SampleViewModel() },
              SampleEntry { SampleViewModel() },
            ),
          )
        }

      failure.message.orEmpty() shouldContain "SampleViewModel"
    }

    test("requesting an unregistered ViewModel fails loudly instead of returning null") {
      val factory = ScreenViewModelFactory(setOf(SampleEntry { SampleViewModel() }))

      val failure =
        shouldThrowAny {
          factory.create(OtherViewModel::class, CreationExtras.Empty)
        }

      failure.message.orEmpty() shouldContain "OtherViewModel"
    }

    test("assisted args reach the entry under the constructor parameter name") {
      val factory =
        ScreenViewModelFactory(
          setOf(SampleEntry { args -> SampleViewModel(args["label"] ?: "missing") }),
        )
      val extras =
        MutableCreationExtras().apply {
          set(
            StarterViewModelFactory.AssistedArgsKey,
            mapToAssistedArgs(mapOf("label" to "from-navigation")),
          )
        }

      val viewModel = factory.create(SampleViewModel::class, extras)

      viewModel.label shouldBe "from-navigation"
    }
  })
