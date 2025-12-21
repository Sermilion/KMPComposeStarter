package com.sermilion.kmpstarter.common

fun <T> T?.require(): T = checkNotNull(this) {
  "Required value was null."
}
