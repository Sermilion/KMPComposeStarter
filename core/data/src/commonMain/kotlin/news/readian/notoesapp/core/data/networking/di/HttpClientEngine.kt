package news.readian.notoesapp.core.data.networking.di

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

expect fun provideHttpClientEngine(): HttpClientEngineFactory<HttpClientEngineConfig>
