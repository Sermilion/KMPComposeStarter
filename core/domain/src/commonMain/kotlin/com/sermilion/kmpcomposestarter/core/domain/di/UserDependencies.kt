package com.sermilion.kmpcomposestarter.core.domain.di

import com.sermilion.kmpcomposestarter.core.domain.auth.TokenStore
import com.sermilion.kmpcomposestarter.core.domain.model.UserData

interface UserDependencies {
  val userData: UserData
  val tokenStore: TokenStore
  val userSessionScope: UserSessionScope
  val userScopedCloseables: Set<UserScopedCloseable>
  val screenComponentFactory: ScreenComponent.Factory
}
