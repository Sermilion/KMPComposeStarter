package news.readian.notoesapp.core.domain.common

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class UuidTest :
  FunSpec({
    test("randomUuid returns a valid uuid") {
      val uuid = randomUuid()

      UUID.fromString(uuid.toString()).toString() shouldBe uuid.toString()
    }
  })
