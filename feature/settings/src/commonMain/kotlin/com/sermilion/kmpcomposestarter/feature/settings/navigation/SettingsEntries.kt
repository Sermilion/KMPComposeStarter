package com.sermilion.kmpcomposestarter.feature.settings.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.core.ui.navigation.injectViewModel
import com.sermilion.kmpcomposestarter.feature.settings.ui.SettingsScreen
import com.sermilion.kmpcomposestarter.feature.settings.viewmodel.SettingsViewModel

/** Binds this feature's route to its screen. See `authEntries` for the shape and why. */
fun EntryProviderScope<Route>.settingsEntries() {
  entry<SettingsRoute> {
    val viewModel = injectViewModel<SettingsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SettingsScreen(uiState = uiState)
  }
}
