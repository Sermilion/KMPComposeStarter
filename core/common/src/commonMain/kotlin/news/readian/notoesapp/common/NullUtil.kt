package news.readian.notoesapp.common

fun <T> T?.require(): T = checkNotNull(this) {
  "Required value was null."
}
