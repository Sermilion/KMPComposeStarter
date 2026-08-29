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
import com.sermilion.kmpcomposestarter.feature.auth.viewmodel.RegisterContract
import kmpcomposestarter.feature.auth.generated.resources.Res
import kmpcomposestarter.feature.auth.generated.resources.register_busy_description
import kmpcomposestarter.feature.auth.generated.resources.register_button_back_to_login
import kmpcomposestarter.feature.auth.generated.resources.register_button_create_account
import kmpcomposestarter.feature.auth.generated.resources.register_button_register_demo
import kmpcomposestarter.feature.auth.generated.resources.register_email_label
import kmpcomposestarter.feature.auth.generated.resources.register_error_network
import kmpcomposestarter.feature.auth.generated.resources.register_error_registration_failed
import kmpcomposestarter.feature.auth.generated.resources.register_error_unknown
import kmpcomposestarter.feature.auth.generated.resources.register_name_label
import kmpcomposestarter.feature.auth.generated.resources.register_password_label
import kmpcomposestarter.feature.auth.generated.resources.register_screen_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RegisterScreen(
  uiState: RegisterContract.UiState,
  onNameChange: (String) -> Unit,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onRegisterClick: () -> Unit,
  onRegisterDemo: () -> Unit,
  onNavigateBack: () -> Unit,
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
      text = stringResource(Res.string.register_screen_title),
      style = MaterialTheme.typography.headlineLarge,
    )
    Spacer(modifier = Modifier.height(32.dp))
    RegisterFormFields(
      uiState = uiState,
      onNameChange = onNameChange,
      onEmailChange = onEmailChange,
      onPasswordChange = onPasswordChange,
      onSubmit = onRegisterClick,
    )
    Spacer(modifier = Modifier.height(32.dp))
    RegisterButtons(
      uiState = uiState,
      onRegisterClick = onRegisterClick,
      onRegisterDemo = onRegisterDemo,
      onNavigateBack = onNavigateBack,
    )
  }
}

@Composable
private fun RegisterFormFields(
  uiState: RegisterContract.UiState,
  onNameChange: (String) -> Unit,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onSubmit: () -> Unit,
) {
  Column {
    AuthFormField(
      value = uiState.name,
      onValueChange = onNameChange,
      label = stringResource(Res.string.register_name_label),
      contentType = ContentType.PersonFullName,
      keyboardType = KeyboardType.Text,
      imeAction = ImeAction.Next,
      enabled = !uiState.isLoading,
    )
    Spacer(modifier = Modifier.height(16.dp))
    AuthFormField(
      value = uiState.email,
      onValueChange = onEmailChange,
      label = stringResource(Res.string.register_email_label),
      contentType = ContentType.EmailAddress,
      keyboardType = KeyboardType.Email,
      imeAction = ImeAction.Next,
      enabled = !uiState.isLoading,
    )
    Spacer(modifier = Modifier.height(16.dp))
    AuthFormField(
      value = uiState.password,
      onValueChange = onPasswordChange,
      label = stringResource(Res.string.register_password_label),
      contentType = ContentType.NewPassword,
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
private fun RegisterContract.Error.message(): String =
  stringResource(
    when (this) {
      RegisterContract.Error.RegistrationFailed -> Res.string.register_error_registration_failed
      RegisterContract.Error.Network -> Res.string.register_error_network
      RegisterContract.Error.Unknown -> Res.string.register_error_unknown
    },
  )

@Composable
private fun RegisterButtons(
  uiState: RegisterContract.UiState,
  onRegisterClick: () -> Unit,
  onRegisterDemo: () -> Unit,
  onNavigateBack: () -> Unit,
) {
  Column {
    Button(
      onClick = onRegisterClick,
      modifier = Modifier.fillMaxWidth(),
      enabled =
        !uiState.isLoading &&
          uiState.name.isNotBlank() &&
          uiState.email.isNotBlank() &&
          uiState.password.isNotBlank(),
    ) {
      if (uiState.isLoading) {
        ButtonBusyIndicator(
          label = stringResource(Res.string.register_button_create_account),
          stateDescription = stringResource(Res.string.register_busy_description),
        )
      } else {
        Text(stringResource(Res.string.register_button_create_account))
      }
    }
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedButton(
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

private val filledRegisterState =
  RegisterContract.UiState(
    name = "Ada Lovelace",
    email = "ada@example.com",
    password = "correct-horse",
  )

@Composable
private fun RegisterScreenPreviewHost(
  darkTheme: Boolean,
  uiState: RegisterContract.UiState,
) {
  StarterTheme(darkTheme = darkTheme) {
    RegisterScreen(
      uiState = uiState,
      onNameChange = {},
      onEmailChange = {},
      onPasswordChange = {},
      onRegisterClick = {},
      onRegisterDemo = {},
      onNavigateBack = {},
    )
  }
}

@Preview
@Composable
internal fun RegisterScreenEmptyPreview() =
  RegisterScreenPreviewHost(darkTheme = false, uiState = RegisterContract.UiState())

@Preview
@Composable
internal fun RegisterScreenLightPreview() =
  RegisterScreenPreviewHost(darkTheme = false, uiState = filledRegisterState)

@Preview
@Composable
internal fun RegisterScreenDarkPreview() =
  RegisterScreenPreviewHost(darkTheme = true, uiState = filledRegisterState)

@Preview
@Composable
internal fun RegisterScreenLoadingPreview() =
  RegisterScreenPreviewHost(darkTheme = true, uiState = filledRegisterState.copy(isLoading = true))

@Preview
@Composable
internal fun RegisterScreenErrorPreview() =
  RegisterScreenPreviewHost(
    darkTheme = false,
    uiState = filledRegisterState.copy(error = RegisterContract.Error.RegistrationFailed),
  )
