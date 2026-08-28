package com.sermilion.kmpcomposestarter.codegen

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

private fun viewModelSource(body: String) = SourceFile.kotlin("TestViewModels.kt", body)

class ViewModelInjectProcessorTest :
  FunSpec({

    test("a plain ViewModel gets one entry bound into its scope") {
      val result = compileWithProcessor(
        viewModelSource(
          """
          package com.example.testapp

          import androidx.lifecycle.ViewModel
          import com.example.testapp.di.ContributesViewModel
          import software.amazon.lastmile.kotlin.inject.anvil.AppScope

          @ContributesViewModel(AppScope::class)
          class PlainViewModel : ViewModel()
          """.trimIndent(),
        ),
      )

      result.exitCode shouldBe KotlinCompilation.ExitCode.OK

      val generated = result.generatedSource("PlainViewModel_Entry.kt")
      generated shouldContain "AppScope::class"
      generated shouldContain "multibinding = true"
      generated shouldContain "savedStateHandleArgName: String? = null"
      generated shouldContain "return create()"
    }

    test("an assisted arg is looked up under its constructor parameter name") {
      val result = compileWithProcessor(
        viewModelSource(
          """
          package com.example.testapp

          import androidx.lifecycle.ViewModel
          import com.example.testapp.di.ContributesViewModel
          import com.example.testapp.di.ScreenScope
          import me.tatarka.inject.annotations.Assisted

          @ContributesViewModel(ScreenScope::class)
          class DetailViewModel(@Assisted private val itemId: String) : ViewModel()
          """.trimIndent(),
        ),
      )

      result.exitCode shouldBe KotlinCompilation.ExitCode.OK

      val generated = result.generatedSource("DetailViewModel_Entry.kt")
      generated shouldContain """args["itemId"]"""
      generated shouldContain "ScreenScope::class"
    }

    test("a SavedStateHandle is published and read under its own parameter name") {
      val result = compileWithProcessor(
        viewModelSource(
          """
          package com.example.testapp

          import androidx.lifecycle.SavedStateHandle
          import androidx.lifecycle.ViewModel
          import com.example.testapp.di.ContributesViewModel
          import com.example.testapp.di.ScreenScope
          import me.tatarka.inject.annotations.Assisted

          @ContributesViewModel(ScreenScope::class)
          class StatefulViewModel(@Assisted private val handle: SavedStateHandle) : ViewModel()
          """.trimIndent(),
        ),
      )

      result.exitCode shouldBe KotlinCompilation.ExitCode.OK

      val generated = result.generatedSource("StatefulViewModel_Entry.kt")
      generated shouldContain """savedStateHandleArgName: String? = "handle""""
      generated shouldContain """args["handle"]"""
    }

    test("a SavedStateHandle that is not @Assisted fails the build") {
      val result = compileWithProcessor(
        viewModelSource(
          """
          package com.example.testapp

          import androidx.lifecycle.SavedStateHandle
          import androidx.lifecycle.ViewModel
          import com.example.testapp.di.ContributesViewModel
          import com.example.testapp.di.ScreenScope

          @ContributesViewModel(ScreenScope::class)
          class ImplicitStateViewModel(private val handle: SavedStateHandle) : ViewModel()
          """.trimIndent(),
        ),
      )

      result.exitCode shouldBe KotlinCompilation.ExitCode.COMPILATION_ERROR
      result.messages shouldContain "must be annotated @Assisted"
    }

    test("an unknown scope fails the build instead of silently defaulting") {
      val result = compileWithProcessor(
        viewModelSource(
          """
          package com.example.testapp

          import androidx.lifecycle.ViewModel
          import com.example.testapp.di.ContributesViewModel

          annotation class MysteryScope

          @ContributesViewModel(MysteryScope::class)
          class MysteriousViewModel : ViewModel()
          """.trimIndent(),
        ),
      )

      result.exitCode shouldBe KotlinCompilation.ExitCode.COMPILATION_ERROR
      result.messages shouldContain "MysteryScope"
      result.generatedSourceOrNull("MysteriousViewModel_Entry.kt") shouldBe null
    }

    test("a symbol that only resolves in a later round is deferred, not dropped") {
      val result = compileWithProcessor(
        viewModelSource(
          """
          package com.example.testapp

          import androidx.lifecycle.ViewModel
          import com.example.testapp.di.ContributesViewModel
          import com.example.testapp.di.ScreenScope
          import me.tatarka.inject.annotations.Assisted

          @ContributesViewModel(ScreenScope::class)
          class DeferredViewModel(@Assisted private val late: LateType) : ViewModel()
          """.trimIndent(),
        ),
        extraProcessors = listOf(LateTypeProcessorProvider()),
      )

      result.exitCode shouldBe KotlinCompilation.ExitCode.OK
      result.generatedSource("DeferredViewModel_Entry.kt") shouldContain """args["late"]"""
    }

    test("the processor honours a non-default di.package option") {
      val diPackage = "com.other.project.wiring"
      val result = compileWithProcessor(
        viewModelSource(
          """
          package com.example.testapp

          import androidx.lifecycle.ViewModel
          import com.other.project.wiring.ContributesViewModel
          import software.amazon.lastmile.kotlin.inject.anvil.AppScope

          @ContributesViewModel(AppScope::class)
          class RelocatedViewModel : ViewModel()
          """.trimIndent(),
        ),
        diPackage = diPackage,
      )

      result.exitCode shouldBe KotlinCompilation.ExitCode.OK
      result.generatedSource("RelocatedViewModel_Entry.kt") shouldContain
        "import com.other.project.wiring.ViewModelEntry"
    }
  })
