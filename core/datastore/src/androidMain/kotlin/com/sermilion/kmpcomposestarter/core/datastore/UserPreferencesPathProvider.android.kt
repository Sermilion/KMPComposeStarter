package com.sermilion.kmpcomposestarter.core.datastore

import android.app.Application
import me.tatarka.inject.annotations.Inject
import okio.Path
import okio.Path.Companion.toOkioPath
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
actual class UserPreferencesPathProvider(
  private val application: Application,
) {
  actual fun userPreferencesPath(): Path =
    application.filesDir.resolve(USER_PREFERENCES_FILE_NAME).toOkioPath()
}
