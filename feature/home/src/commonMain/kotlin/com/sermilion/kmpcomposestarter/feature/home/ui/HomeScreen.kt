package com.sermilion.kmpcomposestarter.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sermilion.kmpcomposestarter.feature.home.viewmodel.HomeContract
import kmpcomposestarter.feature.home.generated.resources.Res
import kmpcomposestarter.feature.home.generated.resources.home_button_go_to_profile
import kmpcomposestarter.feature.home.generated.resources.home_screen_title
import kmpcomposestarter.feature.home.generated.resources.home_welcome_message
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(uiState: HomeContract.UiState, onNavigateToProfile: () -> Unit) {
  HomeScreenContent(
    uiState = uiState,
    onNavigateToProfile = onNavigateToProfile,
  )
}

@Composable
private fun HomeScreenContent(uiState: HomeContract.UiState, onNavigateToProfile: () -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      text = stringResource(Res.string.home_screen_title),
      style = MaterialTheme.typography.headlineLarge,
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = stringResource(Res.string.home_welcome_message, uiState.userName),
      style = MaterialTheme.typography.bodyLarge,
    )

    Text(
      text = uiState.userEmail,
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    Spacer(modifier = Modifier.height(32.dp))

    Button(
      onClick = onNavigateToProfile,
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text(stringResource(Res.string.home_button_go_to_profile))
    }
  }
}
