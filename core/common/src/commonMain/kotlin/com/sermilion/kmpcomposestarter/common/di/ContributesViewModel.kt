package com.sermilion.kmpcomposestarter.common.di

import kotlin.reflect.KClass

/**
 * Registers the annotated `androidx.lifecycle.ViewModel` in [scope].
 *
 * The KSP processor generates one [ViewModelEntry] multibinding per annotated class, so no
 * hand-written registration module is required. [scope] must be `AppScope`, [UserScope] or
 * [ScreenScope]; any other scope fails the build.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class ContributesViewModel(
  val scope: KClass<*>,
)
