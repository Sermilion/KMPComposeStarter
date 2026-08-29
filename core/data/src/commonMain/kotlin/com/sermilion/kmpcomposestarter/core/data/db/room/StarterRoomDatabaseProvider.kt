package com.sermilion.kmpcomposestarter.core.data.db.room

import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.common.coroutines.DispatcherProvider
import com.sermilion.kmpcomposestarter.core.data.db.DatabaseProvider
import com.sermilion.kmpcomposestarter.core.data.db.UserDatabase
import com.sermilion.kmpcomposestarter.core.data.db.createUserDatabase
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import me.tatarka.inject.annotations.Inject
import okio.ByteString.Companion.encodeUtf8
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class StarterRoomDatabaseProvider(
  private val builderFactory: PlatformRoomDatabaseBuilderFactory,
  private val dispatcherProvider: DispatcherProvider,
) : DatabaseProvider {
  private val databaseCache = mutableMapOf<String, UserDatabase>()
  private val cacheLock = SynchronizedObject()

  override fun provideUserDatabase(userId: String): UserDatabase {
    val databaseFileName = userDatabaseFileName(userId)
    return synchronized(cacheLock) {
      databaseCache.getOrPut(databaseFileName) {
        createUserDatabase(
          builderFactory.createUserDatabaseBuilder(
            databaseFileName = databaseFileName,
            queryContext = dispatcherProvider.io,
          ),
        )
      }
    }
  }

  override fun closeDatabaseForUser(userId: String) {
    val databaseFileName = userDatabaseFileName(userId)
    synchronized(cacheLock) { databaseCache.remove(databaseFileName) }?.close()
  }

  override fun deleteDatabaseForUser(userId: String): Boolean {
    val databaseFileName = userDatabaseFileName(userId)
    // Closed before the files are touched: deleting a file an open connection still holds either
    // fails outright or leaves that connection writing to an orphaned inode. The already-handed-out
    // instance cannot be un-closed, which is what makes a failed deletion terminal for this
    // session's reads rather than something the caller can retry into a working state.
    synchronized(cacheLock) { databaseCache.remove(databaseFileName) }?.close()

    val deleted = builderFactory.deleteDatabaseFile(databaseFileName)
    if (!deleted) {
      // The file name is a digest, so this says which database survived without naming the user.
      Logger.w(TAG) { "Delete-my-data left files behind for database $databaseFileName" }
    }
    return deleted
  }

  private companion object {
    const val TAG = "StarterRoomDatabaseProvider"
  }
}

/**
 * Database file name for [userId].
 *
 * The id is hashed rather than interpolated: user ids are opaque server strings that may contain
 * path separators, and a raw id in a file name would also print the user into every disk error.
 */
internal fun userDatabaseFileName(userId: String): String =
  "user_${userId.encodeUtf8().sha256().hex()}.db"
