package com.sermilion.kmpcomposestarter.feature.home.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.core.ui.navigation.injectViewModel
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailRoute
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailScreen
import com.sermilion.kmpcomposestarter.feature.home.detail.DetailViewModel
import com.sermilion.kmpcomposestarter.feature.home.ui.HomeScreen
import com.sermilion.kmpcomposestarter.feature.home.viewmodel.HomeContract
import com.sermilion.kmpcomposestarter.feature.home.viewmodel.HomeViewModel

/** Binds this feature's routes to its screens. See `authEntries` for the shape and why. */
fun EntryProviderScope<Route>.homeEntries(
  onNavigateToProfile: () -> Unit,
  onOpenDetail: (id: String) -> Unit,
  onNavigateBack: () -> Unit,
) {
  entry<HomeRoute> {
    val viewModel = injectViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
      viewModel.effects.collect { event ->
        when (event) {
          HomeContract.Event.NavigateToProfile -> onNavigateToProfile()
          is HomeContract.Event.NavigateToDetail -> onOpenDetail(event.id)
        }
      }
    }
    HomeScreen(
      uiState = uiState,
      onNavigateToProfile = viewModel::navigateToProfile,
      onOpenDetail = viewModel::openDetail,
    )
  }

  entry<DetailRoute> { route ->
    val viewModel = injectViewModel<DetailViewModel>(assisted = mapOf("id" to route.id))
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DetailScreen(
      uiState = uiState,
      onNoteChange = viewModel::onNoteChange,
      onNavigateBack = onNavigateBack,
    )
  }
}
