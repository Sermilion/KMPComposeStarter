package com.sermilion.kmpcomposestarter.core.domain.di

import com.sermilion.kmpcomposestarter.common.di.ScreenComponent
import com.sermilion.kmpcomposestarter.common.di.UserScopedCloseable
import com.sermilion.kmpcomposestarter.core.domain.auth.TokenStore
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.core.domain.repository.UserRepository

/** Everything one signed-in session owns. Lives and dies with that session. */
interface UserDependencies {
  val userData: UserData
  val tokenStore: TokenStore
  val userRepository: UserRepository
  val userSessionScope: UserSessionScope
  val userScopedCloseables: Set<UserScopedCloseable>
  val screenComponentFactory: ScreenComponent.Factory
}
