package news.readian.notoesapp.core.data.util

import co.touchlab.kermit.Logger
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <T> withRestErrorHandling(
  tag: String = "RestError",
  crossinline block: suspend () -> T,
  crossinline errorBlock: suspend (Exception) -> T,
): T = try {
  block()
} catch (e: CancellationException) {
  throw e
} catch (e: Exception) {
  Logger.e(tag, e) { "API error: ${e.message}" }
  errorBlock(e)
}
