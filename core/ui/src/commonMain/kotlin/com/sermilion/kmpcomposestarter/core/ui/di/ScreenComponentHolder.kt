package com.sermilion.kmpcomposestarter.core.ui.di

import androidx.lifecycle.ViewModel
import com.sermilion.kmpcomposestarter.common.di.ScreenComponentProvider

/**
 * Keeps one screen component per nav entry.
 *
 * Storing it in the entry's [androidx.lifecycle.ViewModelStore] gives the component exactly the
 * entry's lifetime: every `@SingleIn(ScreenScope)` binding resolved on that screen comes from the
 * same component instance, and the component is dropped when the entry leaves the back stack.
 */
class ScreenComponentHolder(
  val provider: ScreenComponentProvider,
) : ViewModel()
