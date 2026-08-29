package com.sermilion.kmpcomposestarter.common.coroutines

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.seconds

class EffectTest :
  FunSpec({

    /**
     * A screen that queued two navigations and left on the first used to replay the second the
     * next time it entered composition, bouncing the user straight back out. What a collector
     * never read dies with it — and the channel keeps working for whatever is emitted next.
     */
    test("an effect left unread when its collector goes away is not replayed to the next one") {
      runTest {
        val effect = Effect<String>()
        effect.emit("first")
        effect.emit("second")

        effect.flow.first() shouldBe "first"

        withTimeoutOrNull(1.seconds) { effect.flow.first() } shouldBe null

        effect.emit("third")
        effect.flow.first() shouldBe "third"
      }
    }
  })
