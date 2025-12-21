package com.sermilion.kmpcomposestarter.core.domain.di

import com.sermilion.kmpcomposestarter.core.domain.model.UserData

interface UserDependencies {
  val userData: UserData
  val screenComponentFactory: ScreenComponent.Factory
}
