package com.sermilion.kmpcomposestarter.core.domain.model

/**
 * Mock-only credentials the demo buttons prefill. Injected so presentation code never reaches
 * into the data layer's mock configuration.
 */
data class DemoCredentials(
  val loginEmail: String,
  val password: String,
  val newUserEmail: String,
  val newUserName: String,
)
