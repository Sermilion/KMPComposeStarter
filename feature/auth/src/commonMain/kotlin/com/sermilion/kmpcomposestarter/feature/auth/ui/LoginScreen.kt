package com.sermilion.kmpcomposestarter.feature.auth.ui

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.LoginContract
import kmpcomposestarter.feature.auth.generated.resources.Res
import kmpcomposestarter.feature.auth.generated.resources.login_button_create_account
import kmpcomposestarter.feature.auth.generated.resources.login_button_sign_in
import kmpcomposestarter.feature.auth.generated.resources.login_button_sign_in_demo
import kmpcomposestarter.feature.auth.generated.resources.login_email_label
import kmpcomposestarter.feature.auth.generated.resources.login_error_invalid_credentials
import kmpcomposestarter.feature.auth.generated.resources.login_password_label
import kmpcomposestarter.feature.auth.generated.resources.login_screen_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
  uiState: LoginContract.UiState,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onLoginClick: () -> Unit,
  onDemoLoginClick: () -> Unit,
  onNavigateToRegister: () -> Unit,
) {
  LoginScreenContent(
    uiState = uiState,
    onEmailChange = onEmailChange,
    onPasswordChange = onPasswordChange,
    onLoginClick = onLoginClick,
    onDemoLoginClick = onDemoLoginClick,
    onNavigateToRegister = onNavigateToRegister,
  )
}

@Composable
private fun LoginScreenContent(
  uiState: LoginContract.UiState,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onLoginClick: () -> Unit,
  onDemoLoginClick: () -> Unit,
  onNavigateToRegister: () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      text = stringResource(Res.string.login_screen_title),
      style = MaterialTheme.typography.headlineLarge,
    )
    Spacer(modifier = Modifier.height(32.dp))
    LoginFormFields(
      uiState = uiState,
      onEmailChange = onEmailChange,
      onPasswordChange = onPasswordChange,
    )
    Spacer(modifier = Modifier.height(32.dp))
    LoginButtons(
      uiState = uiState,
      onLoginClick = onLoginClick,
      onDemoLoginClick = onDemoLoginClick,
      onNavigateToRegister = onNavigateToRegister,
    )
  }
}

@Composable
private fun LoginFormFields(
  uiState: LoginContract.UiState,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
) {
  Column {
    OutlinedTextField(
      value = uiState.email,
      onValueChange = onEmailChange,
      label = { Text(stringResource(Res.string.login_email_label)) },
      modifier = Modifier.fillMaxWidth(),
      singleLine = true,
      enabled = !uiState.isLoading,
    )
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedTextField(
      value = uiState.password,
      onValueChange = onPasswordChange,
      label = { Text(stringResource(Res.string.login_password_label)) },
      modifier = Modifier.fillMaxWidth(),
      singleLine = true,
      visualTransformation = PasswordVisualTransformation(),
      enabled = !uiState.isLoading,
    )
    uiState.error?.let {
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = stringResource(Res.string.login_error_invalid_credentials),
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
      )
    }
  }
}

@Composable
private fun LoginButtons(
  uiState: LoginContract.UiState,
  onLoginClick: () -> Unit,
  onDemoLoginClick: () -> Unit,
  onNavigateToRegister: () -> Unit,
) {
  Column {
    if (uiState.isLoading) {
      CircularProgressIndicator()
    } else {
      Button(
        onClick = onLoginClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = uiState.email.isNotBlank() && uiState.password.isNotBlank(),
      ) {
        Text(stringResource(Res.string.login_button_sign_in))
      }
      Spacer(modifier = Modifier.height(8.dp))
      OutlinedButton(
        onClick = onDemoLoginClick,
        modifier = Modifier.fillMaxWidth(),
      ) {
        Text(stringResource(Res.string.login_button_sign_in_demo))
      }
    }
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedButton(
      onClick = onNavigateToRegister,
      modifier = Modifier.fillMaxWidth(),
      enabled = !uiState.isLoading,
    ) {
      Text(stringResource(Res.string.login_button_create_account))
    }
  }
}
