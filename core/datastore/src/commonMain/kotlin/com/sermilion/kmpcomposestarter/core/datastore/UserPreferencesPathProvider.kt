package com.sermilion.kmpcomposestarter.core.datastore

import okio.Path

/**
 * Resolves where [USER_PREFERENCES_FILE_NAME] lives, mirroring the per-user Room database paths:
 * app files on Android, the documents directory on iOS, and a dot-directory under the user's home
 * on desktop.
 */
expect class UserPreferencesPathProvider {
  fun userPreferencesPath(): Path
}
