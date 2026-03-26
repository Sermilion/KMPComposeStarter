package news.readian.notoesapp.common

fun CharSequence?.isValidEmail(): Boolean {
  if (isNullOrEmpty()) {
    return false
  }
  return EMAIL_PATTERN.toRegex().matches(this)
}

fun CharSequence?.isValidPassword(): Boolean {
  if (isNullOrEmpty()) {
    return false
  }
  return PASSWORD_PATTERN.toRegex().matches(this)
}

private const val EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[!@#$%^&*()])[\\w!@#$%^&*()]{8,}$"
