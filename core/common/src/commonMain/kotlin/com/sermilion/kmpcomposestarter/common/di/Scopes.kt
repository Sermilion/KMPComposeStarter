package com.sermilion.kmpcomposestarter.common.di

import me.tatarka.inject.annotations.Scope
import software.amazon.lastmile.kotlin.inject.anvil.AppScope

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class SingleIn(val scope: kotlin.reflect.KClass<*>)

abstract class UserScope private constructor()

abstract class UserFeatureScope private constructor()
