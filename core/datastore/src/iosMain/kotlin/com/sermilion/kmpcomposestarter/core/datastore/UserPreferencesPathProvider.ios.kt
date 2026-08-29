package com.sermilion.kmpcomposestarter.core.datastore

import kotlinx.cinterop.ExperimentalForeignApi
import me.tatarka.inject.annotations.Inject
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@OptIn(ExperimentalForeignApi::class)
@Inject
@SingleIn(AppScope::class)
actual class UserPreferencesPathProvider {
  actual fun userPreferencesPath(): Path {
    val documentDirectory =
      NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = true,
        error = null,
      )

    return "${requireNotNull(documentDirectory?.path)}/$USER_PREFERENCES_FILE_NAME".toPath()
  }
}
