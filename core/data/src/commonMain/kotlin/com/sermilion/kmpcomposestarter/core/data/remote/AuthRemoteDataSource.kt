package com.sermilion.kmpcomposestarter.core.data.remote

import com.sermilion.kmpcomposestarter.core.data.model.AuthResultDataModel

/**
 * The backend's side of authentication.
 *
 * There is deliberately no `refreshToken` or `getCurrentUser` here. The only implementation the
 * starter ships is a mock with no user store and no issuing authority, so both could only be
 * satisfied by fabricating a token or a user — and a stub that invents credentials is worse than
 * a missing method. A fork with a real backend adds them alongside a real implementation.
 */
interface AuthRemoteDataSource {
  suspend fun login(email: String, password: String): AuthResultDataModel
  suspend fun register(email: String, password: String, name: String): AuthResultDataModel
  suspend fun logout()
}
