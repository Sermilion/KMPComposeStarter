package com.sermilion.kmpcomposestarter.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import co.touchlab.kermit.Logger
import com.sermilion.kmpcomposestarter.common.di.LocalScreenComponentFactory
import com.sermilion.kmpcomposestarter.common.di.LocalViewModelProvider
import com.sermilion.kmpcomposestarter.common.di.mapToAssistedArgs
import com.sermilion.kmpcomposestarter.core.domain.di.DIViewModelFactory
import kotlinx.collections.immutable.persistentMapOf

@Composable
inline fun <reified VM : ViewModel> injectViewModel(
  scope: ViewModelScope = ViewModelScope.Feature,
  key: String? = null,
  assisted: Any? = null,
): VM = when (scope) {
  ViewModelScope.Feature -> injectFeatureScopedViewModel(key, assisted)
  ViewModelScope.Onboarding -> injectOnboardingScopedViewModel()
}

@Composable
@PublishedApi
internal inline fun <reified VM : ViewModel> injectFeatureScopedViewModel(
  key: String? = null,
  assisted: Any? = null,
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

  val screenComponent = remember(screenComponentFactory, key) { screenComponentFactory() }
  val defaultCreationExtras = if (viewModelStoreOwner is HasDefaultViewModelProviderFactory) {
    viewModelStoreOwner.defaultViewModelCreationExtras
  } else {
    CreationExtras.Empty
  }

  val assistedMap = if (assisted != null) {
    persistentMapOf("arguments" to assisted)
  } else {
    persistentMapOf()
  }

  val extras = MutableCreationExtras(defaultCreationExtras).apply {
    set(DIViewModelFactory.AssistedArgsKey, mapToAssistedArgs(assistedMap))
  }

  return viewModel(
    viewModelStoreOwner = viewModelStoreOwner,
    modelClass = VM::class,
    key = key,
    factory = screenComponent.diViewModelFactory,
    extras = extras,
  )
}

@Composable
@PublishedApi
internal inline fun <reified VM : ViewModel> injectOnboardingScopedViewModel(): VM {
  val provider = LocalViewModelProvider.current
  val viewModelStoreOwner = LocalViewModelStoreOwner.current
    ?: error("ViewModelStoreOwner not found")

  val factory = remember(provider) {
    object : ViewModelProvider.Factory {
      override fun <T : ViewModel> create(
        modelClass: kotlin.reflect.KClass<T>,
        extras: CreationExtras,
      ): T {
        val viewModel = provider.provide(modelClass)
        check(modelClass.isInstance(viewModel)) {
          "ViewModelProvider returned ${viewModel::class.simpleName} but expected ${modelClass.simpleName}"
        }
        @Suppress("UNCHECKED_CAST")
        return viewModel as T
      }
    }
  }

  return viewModel(
    viewModelStoreOwner = viewModelStoreOwner,
    modelClass = VM::class,
    factory = factory,
  )
}

enum class ViewModelScope {
  Feature,
  Onboarding,
}
