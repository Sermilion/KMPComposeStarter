package com.sermilion.kmpcomposestarter.common.di

import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

/**
 * One DI component per nav entry: the scope every `@SingleIn(ScreenScope::class)` binding resolves
 * from, created by the session that owns the screen and dropped when the entry leaves the stack.
 *
 * It lives in `core:common` next to [StarterViewModelFactory] and [ScreenScope], the two types it
 * is defined in terms of, rather than in `core:domain` - a domain module that cannot compile
 * without `androidx.lifecycle` and the DI framework is not the framework-light layer the
 * architecture docs describe.
 */
@ContributesSubcomponent(ScreenScope::class)
@SingleIn(ScreenScope::class)
interface ScreenComponent : ScreenComponentProvider {
  override val viewModelFactory: StarterViewModelFactory

  @ContributesSubcomponent.Factory(UserScope::class)
  interface Factory {
    fun create(): ScreenComponent
  }
}
