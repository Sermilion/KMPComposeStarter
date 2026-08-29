package com.sermilion.kmpcomposestarter.core.domain.di

import com.sermilion.kmpcomposestarter.core.domain.auth.TokenStore
import com.sermilion.kmpcomposestarter.core.domain.model.UserData
import com.sermilion.kmpcomposestarter.core.domain.repository.UserRepository

interface UserDependencies {
  val userData: UserData
  val tokenStore: TokenStore
  val userRepository: UserRepository
  val userSessionScope: UserSessionScope
  val userScopedCloseables: Set<UserScopedCloseable>
  val screenComponentFactory: ScreenComponent.Factory
}
