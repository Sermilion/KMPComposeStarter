package news.readian.notoesapp.core.data.networking.di

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

actual fun provideHttpClientEngine(): HttpClientEngineFactory<HttpClientEngineConfig> =
  Darwin as HttpClientEngineFactory<HttpClientEngineConfig>
