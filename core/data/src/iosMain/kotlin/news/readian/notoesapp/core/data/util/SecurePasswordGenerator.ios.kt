package news.readian.notoesapp.core.data.util

import platform.Foundation.NSUUID

internal actual fun generateSecurePassword(length: Int): String {
  val builder = StringBuilder()

  while (builder.length < length) {
    builder.append(NSUUID().UUIDString.lowercase().replace("-", ""))
  }

  return builder.substring(0, length)
}
