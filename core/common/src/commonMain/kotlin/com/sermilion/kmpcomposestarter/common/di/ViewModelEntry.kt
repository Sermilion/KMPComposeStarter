package com.sermilion.kmpcomposestarter.common.di

import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

interface ViewModelEntry {
  val kclass: KClass<out ViewModel>
  fun create(args: AssistedArgs): ViewModel
}

interface AssistedArgs {
  operator fun <T> get(name: String): T?
}

object EmptyAssistedArgs : AssistedArgs {
  override fun <T> get(name: String): T? = null
}

fun mapToAssistedArgs(map: Map<String, Any?>): AssistedArgs = object : AssistedArgs {
  @Suppress("UNCHECKED_CAST")
  override fun <T> get(name: String): T? = map[name] as T?
}
