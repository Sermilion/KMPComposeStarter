package com.sermilion.kmpcomposestarter.core.data.util

import co.touchlab.kermit.Logger
import kotlin.coroutines.cancellation.CancellationException

/**
 * Wraps a REST call, converting failures via [errorBlock]. Cancellation is rethrown untouched: a
 * cancelled request is not a failed request, and swallowing it would surface as a fake error and
 * break structured concurrency.
 */
suspend inline fun <T> withRestErrorHandling(
  tag: String = "RestError",
  crossinline block: suspend () -> T,
  crossinline errorBlock: (Exception) -> T,
): T =
  try {
    block()
  } catch (e: CancellationException) {
    throw e
  } catch (e: Exception) {
    Logger.e(tag, e) { "API error: ${e.message}" }
    errorBlock(e)
  }
