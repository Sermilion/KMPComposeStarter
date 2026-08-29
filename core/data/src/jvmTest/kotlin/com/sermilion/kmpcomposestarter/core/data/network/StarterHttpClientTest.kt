package com.sermilion.kmpcomposestarter.core.data.network

import com.sermilion.kmpcomposestarter.core.domain.auth.AuthToken
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotContain
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest

private const val SECRET_TOKEN = "super-secret-access-token"

private class RecordingLogger : Logger {
  val messages = mutableListOf<String>()

  override fun log(message: String) {
    messages += message
  }
}

/**
 * Drives the exact configuration the app ships, over [MockEngine]. Testing a separately assembled
 * client would prove nothing about what production sends.
 */
private suspend fun requestWith(
  logger: Logger,
  loadToken: suspend () -> AuthToken?,
): Pair<String?, String> {
  var authorization: String? = null
  var requestUrl = ""
  val client = HttpClient(MockEngine) {
    engine {
      addHandler { request ->
        authorization = request.headers[HttpHeaders.Authorization]
        requestUrl = request.url.toString()
        respond(
          content = "{}",
          headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
        )
      }
    }
    configureStarterHttpClient(loadToken = loadToken, ktorLogger = logger)
  }

  try {
    client.get("session")
  } finally {
    client.close()
  }
  return authorization to requestUrl
}

class StarterHttpClientTest :
  FunSpec({

    test("requests carry the bearer token and resolve against the configured base URL") {
      runTest {
        val logger = RecordingLogger()

        val (authorization, requestUrl) =
          requestWith(logger) { AuthToken(accessToken = SECRET_TOKEN) }

        authorization shouldBe "Bearer $SECRET_TOKEN"
        requestUrl shouldBe "${NetworkConfig.BASE_URL}session"
        // Ktor's logger prints request headers verbatim unless the header is sanitized, which
        // would put a live credential in every log sink the app ships with.
        logger.messages.joinToString("\n") shouldNotContain SECRET_TOKEN
      }
    }

    test("a request made without a stored token carries no Authorization header") {
      runTest {
        val (authorization, _) = requestWith(RecordingLogger()) { null }

        authorization shouldBe null
      }
    }
  })
