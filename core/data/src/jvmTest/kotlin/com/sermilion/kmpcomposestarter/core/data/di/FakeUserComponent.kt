package com.sermilion.kmpcomposestarter.core.data.di

import com.sermilion.kmpcomposestarter.common.di.ScreenComponent
import com.sermilion.kmpcomposestarter.common.di.UserScopedCloseable
import com.sermilion.kmpcomposestarter.core.domain.auth.AuthToken
import com.sermilion.kmpcomposestarter.core.domain.auth.TokenStore
import com.sermilion.kmpcomposestarter.core.domain.di.UserSessionScope
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.core.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

/** In-memory stand-in for the DataStore-backed token store. */
class FakeTokenStore : TokenStore {
  private val stored = AtomicReference<AuthToken?>(null)

  override suspend fun get(): AuthToken? = stored.get()

  override suspend fun save(token: AuthToken) {
    stored.set(token)
  }

  override suspend fun clear() {
    stored.set(null)
  }
}

/** In-memory stand-in for the Room-backed user repository. */
class FakeUserRepository(
  var deletionSucceeds: Boolean = true,
) : UserRepository {
  private val current = MutableStateFlow<UserData?>(null)

  val savedUsers = mutableListOf<UserData>()

  override fun observeCurrentUser(): Flow<UserData?> = current.asStateFlow()

  override suspend fun saveUser(user: UserData) {
    savedUsers += user
    current.value = user
  }

  override suspend fun deleteMyData(): Boolean {
    if (deletionSucceeds) current.value = null
    return deletionSucceeds
  }
}

/** Stands in for the generated user subcomponent, recording how often teardown touched it. */
class FakeUserComponent(
  override val userData: UserData,
) : UserComponent {
  private val closeCounter = AtomicInteger(0)

  val tokenStoreImpl = FakeTokenStore()

  val userRepositoryImpl = FakeUserRepository()

  val closeCount: Int
    get() = closeCounter.get()

  override val tokenStore: TokenStore = tokenStoreImpl

  override val userRepository: UserRepository = userRepositoryImpl

  override val userSessionScope: UserSessionScope =
    UserSessionScope(CoroutineScope(SupervisorJob()))

  override val userScopedCloseables: Set<UserScopedCloseable> =
    setOf(
      UserScopedCloseable { closeCounter.incrementAndGet() },
    )

  override val screenComponentFactory: ScreenComponent.Factory =
    object : ScreenComponent.Factory {
      override fun create(): ScreenComponent = error("screen components are out of scope here")
    }
}

class RecordingUserComponentFactory : UserComponent.Factory {
  private val createdComponents = mutableListOf<FakeUserComponent>()

  val created: List<FakeUserComponent>
    @Synchronized get() = createdComponents.toList()

  @Synchronized
  override fun create(userData: UserData): UserComponent =
    FakeUserComponent(userData).also(createdComponents::add)
}
