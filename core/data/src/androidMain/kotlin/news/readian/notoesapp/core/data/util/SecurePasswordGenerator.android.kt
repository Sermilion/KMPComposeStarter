package news.readian.notoesapp.core.data.util

import java.security.SecureRandom

private val secureRandom = SecureRandom()
private const val PASSWORD_CHARSET =
  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

internal actual fun generateSecurePassword(length: Int): String = buildString {
  repeat(length) {
    append(PASSWORD_CHARSET[secureRandom.nextInt(PASSWORD_CHARSET.length)])
  }
}
