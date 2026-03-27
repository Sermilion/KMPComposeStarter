package news.readian.notoesapp.core.domain.common

import kotlinx.serialization.Serializable

@Serializable
expect value class Uuid(val value: String) {
  override fun toString(): String
}

expect fun randomUuid(): Uuid

fun String.toUuid(): Uuid = Uuid(this)
