package news.readian.notoesapp.core.domain.common

import kotlinx.serialization.Serializable
import platform.Foundation.NSUUID

@Serializable
actual value class Uuid actual constructor(actual val value: String) {
  actual override fun toString(): String = value
}

actual fun randomUuid(): Uuid = Uuid(NSUUID().UUIDString)
