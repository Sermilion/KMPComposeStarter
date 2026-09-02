package com.sermilion.kmpcomposestarter.core.data.util

import com.sermilion.kmpcomposestarter.core.domain.model.AuthError
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.utils.io.errors.IOException

/**
 * Turns a transport failure into the reason a user is shown.
 *
 * Network failures are separated from everything else on purpose: telling someone with no
 * connection that their password is wrong sends them to reset a password that was never the
 * problem. [AuthError.Network] used to be unreachable — every failure funnelled into
 * [AuthError.Unexpected], so the "check your connection" string could never appear.
 *
 * A fork with a real backend adds its own HTTP-status branches here (401 to
 * [AuthError.InvalidCredentials], say); the timeout and I/O cases below apply to every backend.
 */
internal fun Throwable.toAuthError(): AuthError =
  when (this) {
    is HttpRequestTimeoutException,
    is ConnectTimeoutException,
    is SocketTimeoutException,
    is IOException,
    -> AuthError.Network

    else -> AuthError.Unexpected(this)
  }
