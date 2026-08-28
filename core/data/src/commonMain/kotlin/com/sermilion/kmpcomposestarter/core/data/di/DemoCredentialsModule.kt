package com.sermilion.kmpcomposestarter.core.data.di

import com.sermilion.kmpcomposestarter.core.data.config.MockConfig
import com.sermilion.kmpcomposestarter.core.domain.model.DemoCredentials
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface DemoCredentialsModule {

  @Provides
  @SingleIn(AppScope::class)
  fun provideDemoCredentials(): DemoCredentials = DemoCredentials(
    loginEmail = MockConfig.DEMO_EMAIL,
    password = MockConfig.DEMO_PASSWORD,
    newUserEmail = MockConfig.DEMO_NEW_EMAIL,
    newUserName = MockConfig.DEMO_NEW_USER_NAME,
  )
}
