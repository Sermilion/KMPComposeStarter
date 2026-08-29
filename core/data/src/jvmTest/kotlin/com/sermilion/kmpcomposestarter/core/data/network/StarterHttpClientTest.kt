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

/** What the [MockEngine] handler saw, so a test can assert on the request the client really sent. */
private class RecordedRequest {
  var authorization: String? = null
  var url: String = ""
}

/**
 * Builds the exact configuration the app ships, over [MockEngine]. Testing a separately assembled
 * client would prove nothing about what production sends.
 *
 * The request itself is issued by the caller rather than here: `HttpClient.get` is the only suspend
 * call a helper wrapping it would make, and detekt 1.23.8 cannot resolve it. Ktor 3 publishes
 * Kotlin metadata version 2, which detekt's embedded K1 frontend cannot read, so every Ktor
 * declaration is unresolved during analysis and `RedundantSuspendModifier` flags the `suspend`
 * modifier that the Kotlin compiler in fact requires. Keeping the call in the test's `runTest`
 * block keeps both tools right.
 */
private fun starterClient(
  recorded: RecordedRequest,
  logger: Logger,
  loadToken: suspend () -> AuthToken?,
): HttpClient =
  HttpClient(MockEngine) {
    engine {
      addHandler { request ->
        recorded.authorization = request.headers[HttpHeaders.Authorization]
        recorded.url = request.url.toString()
        respond(
          content = "{}",
          headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
        )
      }
    }
    configureStarterHttpClient(loadToken = loadToken, ktorLogger = logger)
  }

class StarterHttpClientTest :
  FunSpec({

    test("requests carry the bearer token and resolve against the configured base URL") {
      runTest {
        val logger = RecordingLogger()
        val recorded = RecordedRequest()
        val client = starterClient(recorded, logger) { AuthToken(accessToken = SECRET_TOKEN) }

        try {
          client.get("session")
        } finally {
          client.close()
        }

        recorded.authorization shouldBe "Bearer $SECRET_TOKEN"
        recorded.url shouldBe "${NetworkConfig.BASE_URL}session"
        // Ktor's logger prints request headers verbatim unless the header is sanitized, which
        // would put a live credential in every log sink the app ships with.
        logger.messages.joinToString("\n") shouldNotContain SECRET_TOKEN
      }
    }

    test("a request made without a stored token carries no Authorization header") {
      runTest {
        val recorded = RecordedRequest()
        val client = starterClient(recorded, RecordingLogger()) { null }

        try {
          client.get("session")
        } finally {
          client.close()
        }

        recorded.authorization shouldBe null
      }
    }
  })
