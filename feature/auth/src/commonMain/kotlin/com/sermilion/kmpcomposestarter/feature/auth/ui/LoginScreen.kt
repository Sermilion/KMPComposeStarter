package com.sermilion.kmpcomposestarter.feature.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.sermilion.kmpcomposestarter.core.designsystem.component.ButtonBusyIndicator
import com.sermilion.kmpcomposestarter.core.designsystem.component.StarterErrorText
import com.sermilion.kmpcomposestarter.core.designsystem.theme.StarterTheme
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.LoginContract
import kmpcomposestarter.feature.auth.generated.resources.Res
import kmpcomposestarter.feature.auth.generated.resources.login_busy_description
import kmpcomposestarter.feature.auth.generated.resources.login_button_create_account
import kmpcomposestarter.feature.auth.generated.resources.login_button_sign_in
import kmpcomposestarter.feature.auth.generated.resources.login_button_sign_in_demo
import kmpcomposestarter.feature.auth.generated.resources.login_email_label
import kmpcomposestarter.feature.auth.generated.resources.login_error_invalid_credentials
import kmpcomposestarter.feature.auth.generated.resources.login_error_network
import kmpcomposestarter.feature.auth.generated.resources.login_error_unknown
import kmpcomposestarter.feature.auth.generated.resources.login_password_label
import kmpcomposestarter.feature.auth.generated.resources.login_screen_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(
  uiState: LoginContract.UiState,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onLoginClick: () -> Unit,
  onDemoLoginClick: () -> Unit,
  onNavigateToRegister: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier =
      modifier
        .fillMaxSize()
        .imePadding()
        .verticalScroll(rememberScrollState())
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
      onSubmit = onLoginClick,
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
  onSubmit: () -> Unit,
) {
  Column {
    AuthFormField(
      value = uiState.email,
      onValueChange = onEmailChange,
      label = stringResource(Res.string.login_email_label),
      contentType = ContentType.EmailAddress,
      keyboardType = KeyboardType.Email,
      imeAction = ImeAction.Next,
      enabled = !uiState.isLoading,
    )
    Spacer(modifier = Modifier.height(16.dp))
    AuthFormField(
      value = uiState.password,
      onValueChange = onPasswordChange,
      label = stringResource(Res.string.login_password_label),
      contentType = ContentType.Password,
      keyboardType = KeyboardType.Password,
      imeAction = ImeAction.Done,
      enabled = !uiState.isLoading,
      visualTransformation = PasswordVisualTransformation(),
      keyboardActions = KeyboardActions(onDone = { onSubmit() }),
    )
    uiState.error?.let { error ->
      Spacer(modifier = Modifier.height(8.dp))
      StarterErrorText(text = error.message())
    }
  }
}

@Composable
private fun LoginContract.Error.message(): String =
  stringResource(
    when (this) {
      LoginContract.Error.InvalidCredentials -> Res.string.login_error_invalid_credentials
      LoginContract.Error.Network -> Res.string.login_error_network
      LoginContract.Error.Unknown -> Res.string.login_error_unknown
    },
  )

@Composable
private fun LoginButtons(
  uiState: LoginContract.UiState,
  onLoginClick: () -> Unit,
  onDemoLoginClick: () -> Unit,
  onNavigateToRegister: () -> Unit,
) {
  Column {
    Button(
      onClick = onLoginClick,
      modifier = Modifier.fillMaxWidth(),
      enabled =
        !uiState.isLoading &&
          uiState.email.isNotBlank() &&
          uiState.password.isNotBlank(),
    ) {
      if (uiState.isLoading) {
        ButtonBusyIndicator(
          label = stringResource(Res.string.login_button_sign_in),
          stateDescription = stringResource(Res.string.login_busy_description),
        )
      } else {
        Text(stringResource(Res.string.login_button_sign_in))
      }
    }
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedButton(
      onClick = onDemoLoginClick,
      modifier = Modifier.fillMaxWidth(),
      enabled = !uiState.isLoading,
    ) {
      Text(stringResource(Res.string.login_button_sign_in_demo))
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

private val filledLoginState =
  LoginContract.UiState(
    email = "ada@example.com",
    password = "correct-horse",
  )

@Composable
private fun LoginScreenPreviewHost(
  darkTheme: Boolean,
  uiState: LoginContract.UiState,
) {
  StarterTheme(darkTheme = darkTheme) {
    LoginScreen(
      uiState = uiState,
      onEmailChange = {},
      onPasswordChange = {},
      onLoginClick = {},
      onDemoLoginClick = {},
      onNavigateToRegister = {},
    )
  }
}

@Preview
@Composable
internal fun LoginScreenLightPreview() =
  LoginScreenPreviewHost(darkTheme = false, uiState = filledLoginState)

@Preview
@Composable
internal fun LoginScreenDarkPreview() =
  LoginScreenPreviewHost(darkTheme = true, uiState = filledLoginState)

@Preview
@Composable
internal fun LoginScreenLoadingPreview() =
  LoginScreenPreviewHost(darkTheme = false, uiState = filledLoginState.copy(isLoading = true))

@Preview
@Composable
internal fun LoginScreenInvalidCredentialsPreview() =
  LoginScreenPreviewHost(
    darkTheme = false,
    uiState = filledLoginState.copy(error = LoginContract.Error.InvalidCredentials),
  )

@Preview
@Composable
internal fun LoginScreenNetworkErrorPreview() =
  LoginScreenPreviewHost(
    darkTheme = true,
    uiState = filledLoginState.copy(error = LoginContract.Error.Network),
  )

@Preview
@Composable
internal fun LoginScreenUnknownErrorPreview() =
  LoginScreenPreviewHost(
    darkTheme = true,
    uiState = filledLoginState.copy(error = LoginContract.Error.Unknown),
  )
