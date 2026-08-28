package com.sermilion.kmpcomposestarter.core.domain.di

import kotlinx.coroutines.CoroutineScope

/**
 * CoroutineScope that lives exactly as long as the signed-in session. A dedicated type rather
 * than a bare [CoroutineScope] so it can never be confused with the app-wide scope at an
 * injection site.
 */
class UserSessionScope(delegate: CoroutineScope) : CoroutineScope by delegate
