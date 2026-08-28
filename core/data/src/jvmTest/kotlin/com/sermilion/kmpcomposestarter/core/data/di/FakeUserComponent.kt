package com.sermilion.kmpcomposestarter.core.data.di

import com.sermilion.kmpcomposestarter.core.data.auth.InMemoryTokenStore
import com.sermilion.kmpcomposestarter.core.domain.auth.TokenStore
import com.sermilion.kmpcomposestarter.core.domain.di.ScreenComponent
import com.sermilion.kmpcomposestarter.core.domain.di.UserScopedCloseable
import com.sermilion.kmpcomposestarter.core.domain.di.UserSessionScope
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.atomic.AtomicInteger

/** Stands in for the generated user subcomponent, recording how often teardown touched it. */
class FakeUserComponent(override val userData: UserData) : UserComponent {

  private val closeCounter = AtomicInteger(0)

  val tokenStoreImpl = InMemoryTokenStore()

  val closeCount: Int
    get() = closeCounter.get()

  override val tokenStore: TokenStore = tokenStoreImpl

  override val userSessionScope: UserSessionScope =
    UserSessionScope(CoroutineScope(SupervisorJob()))

  override val userScopedCloseables: Set<UserScopedCloseable> = setOf(
    UserScopedCloseable {
      closeCounter.incrementAndGet()
      tokenStoreImpl.close()
    },
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
