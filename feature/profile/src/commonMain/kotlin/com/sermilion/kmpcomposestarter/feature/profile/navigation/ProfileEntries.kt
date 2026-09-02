package com.sermilion.kmpcomposestarter.feature.profile.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.core.ui.navigation.injectViewModel
import com.sermilion.kmpcomposestarter.feature.profile.ui.ProfileScreen
import com.sermilion.kmpcomposestarter.feature.profile.viewmodel.ProfileContract
import com.sermilion.kmpcomposestarter.feature.profile.viewmodel.ProfileViewModel

/** Binds this feature's route to its screen. See `authEntries` for the shape and why. */
fun EntryProviderScope<Route>.profileEntries(onNavigateBack: () -> Unit) {
  entry<ProfileRoute> {
    val viewModel = injectViewModel<ProfileViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
      viewModel.effects.collect { event ->
        when (event) {
          ProfileContract.Event.NavigateBack -> onNavigateBack()
        }
      }
    }
    ProfileScreen(
      uiState = uiState,
      onNavigateBack = viewModel::navigateBack,
      onLogout = viewModel::logout,
      onDeleteMyData = viewModel::deleteMyData,
    )
  }
}
