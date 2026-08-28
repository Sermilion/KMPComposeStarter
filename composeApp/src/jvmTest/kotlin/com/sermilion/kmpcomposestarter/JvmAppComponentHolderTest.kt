package com.sermilion.kmpcomposestarter

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CyclicBarrier

class JvmAppComponentHolderTest :
  FunSpec({

    test("the DI graph is built once per process, even under concurrent access") {
      val accessorCount = 8
      val barrier = CyclicBarrier(accessorCount)

      val components = runBlocking {
        (0 until accessorCount)
          .map {
            async(Dispatchers.Default) {
              barrier.await()
              JvmAppComponentHolder.component
            }
          }
          .awaitAll()
      }

      components.distinct().size shouldBe 1
      JvmAppComponentHolder.component shouldBe components.first()
    }
  })
