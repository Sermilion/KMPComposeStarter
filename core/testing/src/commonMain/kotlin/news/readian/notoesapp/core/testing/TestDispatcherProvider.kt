package news.readian.notoesapp.core.testing

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import news.readian.notoesapp.common.coroutines.DispatcherProvider

class TestDispatcherProvider(testDispatcher: TestDispatcher = StandardTestDispatcher()) :
  DispatcherProvider {
  override val io: CoroutineDispatcher = testDispatcher
  override val main: CoroutineDispatcher = testDispatcher
  override val default: CoroutineDispatcher = testDispatcher
}
