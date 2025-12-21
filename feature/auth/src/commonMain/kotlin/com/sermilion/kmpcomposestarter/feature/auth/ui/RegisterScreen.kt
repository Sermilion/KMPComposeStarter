package com.sermilion.kmpcomposestarter.feature.auth.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.RegisterContract
import kmpcomposestarter.feature.auth.generated.resources.Res
import kmpcomposestarter.feature.auth.generated.resources.register_button_back_to_login
import kmpcomposestarter.feature.auth.generated.resources.register_button_register_demo
import kmpcomposestarter.feature.auth.generated.resources.register_screen_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterScreen(
  uiState: RegisterContract.UiState,
  onRegisterDemo: () -> Unit,
  onNavigateBack: () -> Unit,
) {
  RegisterScreenContent(
    uiState = uiState,
    onRegisterDemo = onRegisterDemo,
    onNavigateBack = onNavigateBack,
  )
}

@Composable
private fun RegisterScreenContent(
  uiState: RegisterContract.UiState,
  onRegisterDemo: () -> Unit,
  onNavigateBack: () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      text = stringResource(Res.string.register_screen_title),
      style = MaterialTheme.typography.headlineLarge,
    )

    Spacer(modifier = Modifier.height(32.dp))

    Button(
      onClick = onRegisterDemo,
      modifier = Modifier.fillMaxWidth(),
      enabled = !uiState.isLoading,
    ) {
      Text(stringResource(Res.string.register_button_register_demo))
    }

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedButton(
      onClick = onNavigateBack,
      modifier = Modifier.fillMaxWidth(),
      enabled = !uiState.isLoading,
    ) {
      Text(stringResource(Res.string.register_button_back_to_login))
    }
  }
}
