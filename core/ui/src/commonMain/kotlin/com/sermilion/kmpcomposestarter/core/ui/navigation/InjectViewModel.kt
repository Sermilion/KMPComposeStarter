package com.sermilion.kmpcomposestarter.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.common.di.LocalScreenComponentFactory
import com.sermilion.kmpcomposestarter.common.di.LocalViewModelFactory
import com.sermilion.kmpcomposestarter.common.di.StarterViewModelFactory
import com.sermilion.kmpcomposestarter.common.di.mapToAssistedArgs
import com.sermilion.kmpcomposestarter.core.ui.di.ScreenComponentHolder

/**
 * Resolves [VM] from the DI graph.
 *
 * [assisted] is keyed by the ViewModel's constructor parameter names — the same keys the
 * generated entry reads.
 */
@Composable
inline fun <reified VM : ViewModel> injectViewModel(
  scope: ViewModelScope = ViewModelScope.Feature,
  key: String? = null,
  assisted: Map<String, Any?> = emptyMap(),
): VM = when (scope) {
  ViewModelScope.Feature -> injectFeatureScopedViewModel(key, assisted)
  ViewModelScope.PreAuth -> injectPreAuthScopedViewModel()
}

@Composable
@PublishedApi
internal inline fun <reified VM : ViewModel> injectFeatureScopedViewModel(
  key: String? = null,
  assisted: Map<String, Any?> = emptyMap(),
): VM {
  val screenComponentFactory = LocalScreenComponentFactory.current

  if (screenComponentFactory == null) {
    Logger.e("InjectViewModel") {
      "ScreenComponentFactory not available. User might not be logged in or " +
        "UserComponent was destroyed. ViewModel: ${VM::class.simpleName}"
    }
    error(
      "ScreenComponentFactory not provided. This screen requires user authentication. " +
        "Ensure user is logged in before navigating to this screen.",
    )
  }

  val viewModelStoreOwner = LocalViewModelStoreOwner.current
    ?: error("ViewModelStoreOwner not found")

  // Deliberately unkeyed: every ViewModel on this nav entry must share one screen component.
  val holder = viewModel<ScreenComponentHolder>(viewModelStoreOwner = viewModelStoreOwner) {
    ScreenComponentHolder(screenComponentFactory())
  }

  val defaultCreationExtras = if (viewModelStoreOwner is HasDefaultViewModelProviderFactory) {
    viewModelStoreOwner.defaultViewModelCreationExtras
  } else {
    CreationExtras.Empty
  }

  val extras = MutableCreationExtras(defaultCreationExtras).apply {
    set(StarterViewModelFactory.AssistedArgsKey, mapToAssistedArgs(assisted))
  }

  return viewModel(
    viewModelStoreOwner = viewModelStoreOwner,
    modelClass = VM::class,
    key = key,
    factory = holder.provider.viewModelFactory,
    extras = extras,
  )
}

@Composable
@PublishedApi
internal inline fun <reified VM : ViewModel> injectPreAuthScopedViewModel(): VM {
  val viewModelStoreOwner = LocalViewModelStoreOwner.current
    ?: error("ViewModelStoreOwner not found")

  return viewModel(
    viewModelStoreOwner = viewModelStoreOwner,
    modelClass = VM::class,
    factory = LocalViewModelFactory.current,
  )
}

enum class ViewModelScope {
  Feature,
  PreAuth,
}
