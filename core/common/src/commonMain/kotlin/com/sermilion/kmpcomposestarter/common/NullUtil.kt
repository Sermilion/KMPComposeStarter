package com.sermilion.kmpcomposestarter.common

fun <T> T?.require(): T = checkNotNull(this) {
  "Required value was null."
}
