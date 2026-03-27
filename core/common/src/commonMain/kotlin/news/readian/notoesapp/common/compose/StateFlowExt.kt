package news.readian.notoesapp.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

@Composable
expect fun <T> StateFlow<T>.collectAsStateMultiplatform(): State<T>

@Composable
expect fun <T> Flow<T>.collectAsStateMultiplatform(initial: T): State<T>

@Composable
expect fun <T> Flow<T>.collectAsStateMultiplatform(initial: T, context: CoroutineContext): State<T>
