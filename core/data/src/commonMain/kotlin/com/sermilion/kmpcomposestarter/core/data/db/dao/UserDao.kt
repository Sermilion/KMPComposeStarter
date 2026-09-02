package com.sermilion.kmpcomposestarter.core.data.db.dao

import com.sermilion.kmpcomposestarter.core.data.model.UserLocalDataModel
import kotlinx.coroutines.flow.Flow

/**
 * The signed-in user's own row.
 *
 * Scoped to one user's database, which holds exactly that user's record - so there is deliberately
 * no list query and no bulk write here. A fork whose database grows a table with many rows should
 * add both single-item and bulk operations for that table rather than widening this one.
 */
interface UserDao {
  fun observeUser(userId: String): Flow<UserLocalDataModel?>

  suspend fun findUser(userId: String): UserLocalDataModel?

  suspend fun insertUser(user: UserLocalDataModel)
}
