package news.readian.notoesapp.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

@Composable
actual fun <T> StateFlow<T>.collectAsStateMultiplatform(): State<T> = collectAsStateWithLifecycle()

@Composable
actual fun <T> Flow<T>.collectAsStateMultiplatform(initial: T): State<T> =
  collectAsStateWithLifecycle(initial)

@Composable
actual fun <T> Flow<T>.collectAsStateMultiplatform(
  initial: T,
  context: CoroutineContext,
): State<T> = collectAsStateWithLifecycle(initialValue = initial, context = context)
