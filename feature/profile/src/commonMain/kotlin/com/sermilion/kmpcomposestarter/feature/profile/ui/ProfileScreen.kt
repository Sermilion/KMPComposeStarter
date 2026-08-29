package com.sermilion.kmpcomposestarter.feature.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sermilion.kmpcomposestarter.core.designsystem.component.ButtonBusyIndicator
import com.sermilion.kmpcomposestarter.core.designsystem.component.StarterErrorText
import com.sermilion.kmpcomposestarter.core.designsystem.theme.StarterTheme
import com.sermilion.kmpcomposestarter.feature.profile.viewmodel.ProfileContract
import kmpcomposestarter.feature.profile.generated.resources.Res
import kmpcomposestarter.feature.profile.generated.resources.profile_button_back_to_home
import kmpcomposestarter.feature.profile.generated.resources.profile_button_delete_my_data
import kmpcomposestarter.feature.profile.generated.resources.profile_button_logout
import kmpcomposestarter.feature.profile.generated.resources.profile_delete_busy_description
import kmpcomposestarter.feature.profile.generated.resources.profile_delete_failed
import kmpcomposestarter.feature.profile.generated.resources.profile_email_label
import kmpcomposestarter.feature.profile.generated.resources.profile_id_label
import kmpcomposestarter.feature.profile.generated.resources.profile_logout_busy_description
import kmpcomposestarter.feature.profile.generated.resources.profile_logout_failed
import kmpcomposestarter.feature.profile.generated.resources.profile_name_label
import kmpcomposestarter.feature.profile.generated.resources.profile_screen_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen(
  uiState: ProfileContract.UiState,
  onNavigateBack: () -> Unit,
  onLogout: () -> Unit,
  onDeleteMyData: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
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
      enabled = !uiState.isBusy,
    ) {
      Text(stringResource(Res.string.profile_button_back_to_home))
    }

    Spacer(modifier = Modifier.height(16.dp))

    SessionActions(
      uiState = uiState,
      onLogout = onLogout,
      onDeleteMyData = onDeleteMyData,
    )
  }
}

/**
 * The two ways out of a session.
 *
 * Both controls stay on screen while one runs, disabled, with the spinner inside the button that
 * is working. Replacing them with a bare indicator moved every other control on the screen, and
 * left nothing to read once the action finished.
 */
@Composable
private fun SessionActions(
  uiState: ProfileContract.UiState,
  onLogout: () -> Unit,
  onDeleteMyData: () -> Unit,
) {
  Column {
    Button(
      onClick = onLogout,
      modifier = Modifier.fillMaxWidth(),
      enabled = !uiState.isBusy,
    ) {
      if (uiState.isLoggingOut) {
        ButtonBusyIndicator(
          stateDescription = stringResource(Res.string.profile_logout_busy_description),
        )
      } else {
        Text(stringResource(Res.string.profile_button_logout))
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    TextButton(
      onClick = onDeleteMyData,
      modifier = Modifier.fillMaxWidth(),
      enabled = !uiState.isBusy,
    ) {
      if (uiState.isDeletingData) {
        ButtonBusyIndicator(
          stateDescription = stringResource(Res.string.profile_delete_busy_description),
        )
      } else {
        Text(
          text = stringResource(Res.string.profile_button_delete_my_data),
          color = MaterialTheme.colorScheme.error,
        )
      }
    }

    if (uiState.dataDeletionFailed) {
      Spacer(modifier = Modifier.height(16.dp))
      StarterErrorText(text = stringResource(Res.string.profile_delete_failed))
    }

    if (uiState.logoutFailed) {
      Spacer(modifier = Modifier.height(16.dp))
      StarterErrorText(text = stringResource(Res.string.profile_logout_failed))
    }
  }
}

private val sampleProfileState = ProfileContract.UiState(
  userName = "Ada Lovelace",
  userEmail = "ada@example.com",
  userId = "user-1",
)

@Composable
private fun ProfileScreenPreviewHost(darkTheme: Boolean, uiState: ProfileContract.UiState) {
  StarterTheme(darkTheme = darkTheme) {
    ProfileScreen(
      uiState = uiState,
      onNavigateBack = {},
      onLogout = {},
      onDeleteMyData = {},
    )
  }
}

@Preview
@Composable
private fun ProfileScreenLightPreview() =
  ProfileScreenPreviewHost(darkTheme = false, uiState = sampleProfileState)

@Preview
@Composable
private fun ProfileScreenDarkPreview() =
  ProfileScreenPreviewHost(darkTheme = true, uiState = sampleProfileState)

@Preview
@Composable
private fun ProfileScreenLoggingOutPreview() = ProfileScreenPreviewHost(
  darkTheme = false,
  uiState = sampleProfileState.copy(isLoggingOut = true),
)

@Preview
@Composable
private fun ProfileScreenDeletionFailedPreview() = ProfileScreenPreviewHost(
  darkTheme = true,
  uiState = sampleProfileState.copy(dataDeletionFailed = true),
)

@Preview
@Composable
private fun ProfileScreenLogoutFailedPreview() = ProfileScreenPreviewHost(
  darkTheme = false,
  uiState = sampleProfileState.copy(logoutFailed = true),
)
