package news.readian.notoesapp.core.data.networking.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import news.readian.notoesapp.core.data.networking.ApiUrls
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface NetworkingModule {

  @Provides
  @SingleIn(AppScope::class)
  fun provideJson(): Json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
  }

  @Provides
  @SingleIn(AppScope::class)
  fun provideHttpClient(json: Json): HttpClient = HttpClient(provideHttpClientEngine()) {
    defaultRequest {
      url(ApiUrls.PRODUCTION)
      contentType(ContentType.Application.Json)
    }

    install(HttpTimeout) {
      requestTimeoutMillis = HTTP_TIMEOUT_MILLIS
      connectTimeoutMillis = HTTP_TIMEOUT_MILLIS
      socketTimeoutMillis = HTTP_TIMEOUT_MILLIS
    }

    install(ContentNegotiation) {
      json(json)
    }

    expectSuccess = true
  }

  private companion object {
    private const val HTTP_TIMEOUT_MILLIS = 30_000L
  }
}
