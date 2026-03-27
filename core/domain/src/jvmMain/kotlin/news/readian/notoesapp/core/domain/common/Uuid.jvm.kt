@file:OptIn(InternalSerializationApi::class)

package news.readian.notoesapp.core.domain.common

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import java.util.UUID as JavaUUID

@Serializable
@JvmInline
actual value class Uuid actual constructor(actual val value: String) {
  actual override fun toString(): String = value
}

fun Uuid.toJavaUUID(): JavaUUID = JavaUUID.fromString(value)

fun JavaUUID.toUuid(): Uuid = Uuid(this.toString())

actual fun randomUuid(): Uuid = Uuid(JavaUUID.randomUUID().toString())
