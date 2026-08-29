package com.sermilion.kmpcomposestarter.core.datastore

import me.tatarka.inject.annotations.Inject
import okio.Path
import okio.Path.Companion.toOkioPath
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import java.io.File

@Inject
@SingleIn(AppScope::class)
actual class UserPreferencesPathProvider {

  actual fun userPreferencesPath(): Path =
    desktopDataDirectory().resolve(USER_PREFERENCES_FILE_NAME).toOkioPath()
}

/**
 * Desktop data directory, shared with the per-user Room databases.
 *
 * `-Dstarter.dataDir=...` overrides it so tests and portable installs never write into the
 * developer's home directory.
 */
private fun desktopDataDirectory(): File {
  val directory = System.getProperty("starter.dataDir")
    ?.let(::File)
    ?: File(System.getProperty("user.home"), ".kmpcomposestarter")
  directory.mkdirs()
  return directory
}
