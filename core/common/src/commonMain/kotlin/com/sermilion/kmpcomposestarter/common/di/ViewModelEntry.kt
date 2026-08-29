package com.sermilion.kmpcomposestarter.common.di

import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

interface ViewModelEntry {
  val kclass: KClass<out ViewModel>

  /**
   * Constructor parameter name of the ViewModel's `SavedStateHandle`, or `null` when it does not
   * take one. The factory uses this to publish the handle under the very key the generated lookup
   * reads, instead of guessing a conventional parameter name.
   */
  val savedStateHandleArgName: String?

  fun create(args: AssistedArgs): ViewModel
}

interface AssistedArgs {
  operator fun <T> get(name: String): T?
}

object EmptyAssistedArgs : AssistedArgs {
  override fun <T> get(name: String): T? = null
}

fun mapToAssistedArgs(map: Map<String, Any?>): AssistedArgs =
  object : AssistedArgs {
    @Suppress("UNCHECKED_CAST")
    override fun <T> get(name: String): T? = map[name] as T?
  }

/** Returns [AssistedArgs] that resolves [key] to [value] and delegates every other name. */
fun AssistedArgs.withArg(
  key: String,
  value: Any?,
): AssistedArgs {
  val delegate = this
  return object : AssistedArgs {
    @Suppress("UNCHECKED_CAST")
    override fun <T> get(name: String): T? = if (name == key) value as T? else delegate[name]
  }
}
