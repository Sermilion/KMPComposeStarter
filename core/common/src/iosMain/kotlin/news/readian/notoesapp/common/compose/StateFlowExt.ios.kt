package news.readian.notoesapp.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

@Composable
actual fun <T> StateFlow<T>.collectAsStateMultiplatform(): State<T> = collectAsState()

@Composable
actual fun <T> Flow<T>.collectAsStateMultiplatform(initial: T): State<T> = collectAsState(initial)

@Composable
actual fun <T> Flow<T>.collectAsStateMultiplatform(
  initial: T,
  context: CoroutineContext,
): State<T> = collectAsState(initial, context)
