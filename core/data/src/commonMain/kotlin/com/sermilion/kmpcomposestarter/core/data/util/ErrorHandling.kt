package com.sermilion.kmpcomposestarter.core.data.util

import co.touchlab.kermit.Logger

suspend inline fun <T> withRestErrorHandling(
  tag: String = "RestError",
  crossinline block: suspend () -> T,
  crossinline errorBlock: (Exception) -> T,
): T = try {
  block()
} catch (e: Exception) {
  Logger.e(tag, e) { "API error: ${e.message}" }
  errorBlock(e)
}
