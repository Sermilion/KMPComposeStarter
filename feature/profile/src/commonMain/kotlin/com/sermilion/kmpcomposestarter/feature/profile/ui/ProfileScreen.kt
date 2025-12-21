package com.sermilion.kmpcomposestarter.feature.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sermilion.kmpcomposestarter.feature.profile.viewmodel.ProfileContract
import kmpcomposestarter.feature.profile.generated.resources.Res
import kmpcomposestarter.feature.profile.generated.resources.profile_button_back_to_home
import kmpcomposestarter.feature.profile.generated.resources.profile_button_logout
import kmpcomposestarter.feature.profile.generated.resources.profile_email_label
import kmpcomposestarter.feature.profile.generated.resources.profile_id_label
import kmpcomposestarter.feature.profile.generated.resources.profile_name_label
import kmpcomposestarter.feature.profile.generated.resources.profile_screen_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfileScreen(
  uiState: ProfileContract.UiState,
  onNavigateBack: () -> Unit,
  onLogout: () -> Unit,
) {
  ProfileScreenContent(
    uiState = uiState,
    onNavigateBack = onNavigateBack,
    onLogout = onLogout,
  )
}

@Composable
private fun ProfileScreenContent(
  uiState: ProfileContract.UiState,
  onNavigateBack: () -> Unit,
  onLogout: () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      text = stringResource(Res.string.profile_screen_title),
      style = MaterialTheme.typography.headlineLarge,
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = stringResource(Res.string.profile_name_label, uiState.userName),
      style = MaterialTheme.typography.bodyLarge,
    )
    Text(
      text = stringResource(Res.string.profile_email_label, uiState.userEmail),
      style = MaterialTheme.typography.bodyMedium,
    )
    Text(
      text = stringResource(Res.string.profile_id_label, uiState.userId),
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    Spacer(modifier = Modifier.height(32.dp))

    OutlinedButton(
      onClick = onNavigateBack,
      modifier = Modifier.fillMaxWidth(),
      enabled = !uiState.isLoggingOut,
    ) {
      Text(stringResource(Res.string.profile_button_back_to_home))
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (uiState.isLoggingOut) {
      CircularProgressIndicator()
    } else {
      Button(
        onClick = onLogout,
        modifier = Modifier.fillMaxWidth(),
      ) {
        Text(stringResource(Res.string.profile_button_logout))
      }
    }
  }
}
