package com.sermilion.kmpcomposestarter.core.domain.model

/**
 * The single authentication error hierarchy. Data sources classify failures into these reasons;
 * only ViewModels turn a reason into something a user reads.
 */
sealed interface AuthError {
  data object InvalidCredentials : AuthError

  data object Network : AuthError

  data object RefreshFailed : AuthError

  data class Unexpected(
    val cause: Throwable?,
  ) : AuthError
}
