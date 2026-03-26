package news.readian.notoesapp.core.data.networking.di

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual fun provideHttpClientEngine(): HttpClientEngineFactory<HttpClientEngineConfig> =
  OkHttp as HttpClientEngineFactory<HttpClientEngineConfig>
