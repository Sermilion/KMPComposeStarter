package com.sermilion.kmpcomposestarter.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

/**
 * Builds the platform's [HttpClient].
 *
 * Each actual names its engine concretely: OkHttp on Android and desktop, Darwin on iOS. The
 * engine's config type is therefore resolved where the engine is known, and nothing hands an
 * `HttpClientEngineFactory<*>` across the expect boundary for a caller to cast back.
 */
internal expect fun createPlatformHttpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient
