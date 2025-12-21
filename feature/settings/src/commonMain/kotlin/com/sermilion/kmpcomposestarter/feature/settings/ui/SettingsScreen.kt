package com.sermilion.kmpcomposestarter.feature.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sermilion.kmpcomposestarter.feature.settings.viewmodel.SettingsContract
import kmpcomposestarter.feature.settings.generated.resources.Res
import kmpcomposestarter.feature.settings.generated.resources.settings_email_label
import kmpcomposestarter.feature.settings.generated.resources.settings_placeholder
import kmpcomposestarter.feature.settings.generated.resources.settings_screen_title
import kmpcomposestarter.feature.settings.generated.resources.settings_user_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsScreen(uiState: SettingsContract.UiState) {
  SettingsScreenContent(uiState = uiState)
}

@Composable
private fun SettingsScreenContent(uiState: SettingsContract.UiState) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      text = stringResource(Res.string.settings_screen_title),
      style = MaterialTheme.typography.headlineLarge,
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = stringResource(Res.string.settings_user_label, uiState.userName),
      style = MaterialTheme.typography.bodyLarge,
    )
    Text(
      text = stringResource(Res.string.settings_email_label, uiState.userEmail),
      style = MaterialTheme.typography.bodyMedium,
    )

    Spacer(modifier = Modifier.height(32.dp))

    Text(
      text = stringResource(Res.string.settings_placeholder),
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}
