package com.sermilion.kmpcomposestarter.core.data.util

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.cancellation.CancellationException

class ErrorHandlingTest :
  FunSpec({

    test("cancellation is rethrown instead of being reported as a request failure") {
      runTest {
        var errorBlockRan = false

        shouldThrow<CancellationException> {
          withRestErrorHandling<String>(
            block = { throw CancellationException("caller went away") },
            errorBlock = {
              errorBlockRan = true
              "converted"
            },
          )
        }

        errorBlockRan shouldBe false
      }
    }

    test("other failures are converted by the error block") {
      runTest {
        val result =
          withRestErrorHandling(
            block = { throw IllegalStateException("boom") },
            errorBlock = { "converted" },
          )

        result shouldBe "converted"
      }
    }
  })
