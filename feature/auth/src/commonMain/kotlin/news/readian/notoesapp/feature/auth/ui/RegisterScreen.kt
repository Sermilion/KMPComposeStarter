package news.readian.notoesapp.feature.auth.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import news.readian.notoesapp.common.isValidEmail
import news.readian.notoesapp.common.isValidPassword
import news.readian.notoesapp.core.designsystem.component.ButtonStyle
import news.readian.notoesapp.core.designsystem.component.ReadianButton
import news.readian.notoesapp.core.designsystem.component.ReadianPasswordField
import news.readian.notoesapp.core.designsystem.component.ReadianTextField
import news.readian.notoesapp.core.ui.composables.ErrorContainer
import news.readian.notoesapp.core.ui.composables.ErrorContent
import news.readian.notoesapp.core.ui.composables.LoadingContent
import news.readian.notoesapp.feature.auth.viewmodel.RegisterContract
import notesapp.feature.auth.generated.resources.Res
import notesapp.feature.auth.generated.resources.error_confirm_password_validation
import notesapp.feature.auth.generated.resources.error_email_validation
import notesapp.feature.auth.generated.resources.error_generic
import notesapp.feature.auth.generated.resources.error_password_validation
import notesapp.feature.auth.generated.resources.error_username_validation
import notesapp.feature.auth.generated.resources.label_confirm_password
import notesapp.feature.auth.generated.resources.label_email
import notesapp.feature.auth.generated.resources.label_password
import notesapp.feature.auth.generated.resources.label_sign_up
import notesapp.feature.auth.generated.resources.label_username
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterScreen(
  uiState: RegisterContract.UiState,
  onNameChange: (String) -> Unit,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onRegisterClick: () -> Unit,
  onNavigateBack: () -> Unit,
  modifier: Modifier = Modifier,
) {
  var confirmPassword by rememberSaveable { mutableStateOf("") }
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }
  val validationState = rememberRegisterValidationState(
    name = uiState.name,
    email = uiState.email,
    password = uiState.password,
    confirmPassword = confirmPassword,
    isLoading = uiState.isLoading,
  )

  Scaffold(
    topBar = { RegisterTopBar(onBackClick = onNavigateBack) },
    contentWindowInsets = WindowInsets.statusBars,
  ) { innerPadding ->
    Box(
      modifier = modifier
        .imePadding()
        .fillMaxSize()
        .padding(innerPadding),
    ) {
      RegisterForm(
        uiState = uiState,
        confirmPassword = confirmPassword,
        passwordVisible = passwordVisible,
        confirmPasswordVisible = confirmPasswordVisible,
        validationState = validationState,
        onNameChange = onNameChange,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onConfirmPasswordChange = { confirmPassword = it },
        onPasswordVisibilityClick = { passwordVisible = !passwordVisible },
        onConfirmPasswordVisibilityClick = {
          confirmPasswordVisible = !confirmPasswordVisible
        },
        onRegisterClick = onRegisterClick,
      )

      if (uiState.isLoading) {
        LoadingContent()
      }
    }
  }
}

@Composable
private fun RegisterForm(
  uiState: RegisterContract.UiState,
  confirmPassword: String,
  passwordVisible: Boolean,
  confirmPasswordVisible: Boolean,
  validationState: RegisterValidationState,
  onNameChange: (String) -> Unit,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onConfirmPasswordChange: (String) -> Unit,
  onPasswordVisibilityClick: () -> Unit,
  onConfirmPasswordVisibilityClick: () -> Unit,
  onRegisterClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    item {
      Text(
        text = stringResource(Res.string.label_sign_up),
        style = MaterialTheme.typography.headlineMedium,
      )
    }

    item {
      ErrorContainer(modifier = Modifier.height(96.dp)) {
        registerErrorMessage(uiState = uiState, validationState = validationState)?.let { message ->
          ErrorContent(message)
        }
      }
    }

    item {
      RegisterFields(
        uiState = uiState,
        confirmPassword = confirmPassword,
        passwordVisible = passwordVisible,
        confirmPasswordVisible = confirmPasswordVisible,
        validationState = validationState,
        onNameChange = onNameChange,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onConfirmPasswordChange = onConfirmPasswordChange,
        onPasswordVisibilityClick = onPasswordVisibilityClick,
        onConfirmPasswordVisibilityClick = onConfirmPasswordVisibilityClick,
      )
    }

    item {
      ReadianButton(
        text = stringResource(Res.string.label_sign_up),
        style = ButtonStyle.Primary,
        onClick = onRegisterClick,
        enabled = validationState.registrationEnabled,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 32.dp)
          .padding(top = 16.dp),
      )
    }

    item {
      AuthTermsView(
        modifier = Modifier
          .padding(horizontal = 80.dp)
          .padding(top = 48.dp, bottom = 16.dp),
      )
    }
  }
}

@Composable
private fun RegisterFields(
  uiState: RegisterContract.UiState,
  confirmPassword: String,
  passwordVisible: Boolean,
  confirmPasswordVisible: Boolean,
  validationState: RegisterValidationState,
  onNameChange: (String) -> Unit,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onConfirmPasswordChange: (String) -> Unit,
  onPasswordVisibilityClick: () -> Unit,
  onConfirmPasswordVisibilityClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(modifier = modifier) {
    ReadianTextField(
      value = uiState.name,
      label = stringResource(Res.string.label_username),
      onValueChange = onNameChange,
      enabled = !uiState.isLoading,
      isError = validationState.hasUsernameValidationError,
      modifier = Modifier.padding(top = 8.dp),
    )

    ReadianTextField(
      value = uiState.email,
      label = stringResource(Res.string.label_email),
      onValueChange = onEmailChange,
      enabled = !uiState.isLoading,
      isError = validationState.hasEmailValidationError,
      modifier = Modifier.padding(top = 80.dp),
    )

    ReadianPasswordField(
      value = uiState.password,
      label = stringResource(Res.string.label_password),
      passwordVisible = passwordVisible,
      onValueChange = onPasswordChange,
      onPasswordVisibilityClick = onPasswordVisibilityClick,
      enabled = !uiState.isLoading,
      isError = validationState.hasPasswordValidationError,
      modifier = Modifier.padding(top = 152.dp),
    )

    ReadianPasswordField(
      value = confirmPassword,
      label = stringResource(Res.string.label_confirm_password),
      passwordVisible = confirmPasswordVisible,
      onValueChange = onConfirmPasswordChange,
      onPasswordVisibilityClick = onConfirmPasswordVisibilityClick,
      enabled = !uiState.isLoading,
      isError = validationState.hasPasswordMatchError,
      modifier = Modifier.padding(top = 224.dp),
    )
  }
}

@Composable
private fun registerErrorMessage(
  uiState: RegisterContract.UiState,
  validationState: RegisterValidationState,
): String? = when {
  validationState.hasUsernameValidationError -> stringResource(Res.string.error_username_validation)
  validationState.hasEmailValidationError -> stringResource(Res.string.error_email_validation)
  validationState.hasPasswordValidationError -> stringResource(Res.string.error_password_validation)
  validationState.hasPasswordMatchError -> stringResource(
    Res.string.error_confirm_password_validation,
  )
  uiState.error is RegisterContract.Error.Unknown -> uiState.error.message.ifBlank {
    stringResource(Res.string.error_generic)
  }
  uiState.error is RegisterContract.Error.RegistrationFailed -> stringResource(
    Res.string.error_generic,
  )
  else -> null
}

@Composable
private fun rememberRegisterValidationState(
  name: String,
  email: String,
  password: String,
  confirmPassword: String,
  isLoading: Boolean,
): RegisterValidationState {
  val hasUsernameValidationError by remember(name) {
    derivedStateOf { name.length < 2 && name.isNotBlank() }
  }
  val hasEmailValidationError by remember(email) {
    derivedStateOf { email.isNotBlank() && !email.isValidEmail() }
  }
  val hasPasswordValidationError by remember(password) {
    derivedStateOf { password.isNotBlank() && !password.isValidPassword() }
  }
  val hasPasswordMatchError by remember(password, confirmPassword) {
    derivedStateOf { confirmPassword.isNotBlank() && password != confirmPassword }
  }
  val registrationEnabled by remember(
    name,
    email,
    password,
    confirmPassword,
    isLoading,
    hasUsernameValidationError,
    hasEmailValidationError,
    hasPasswordValidationError,
    hasPasswordMatchError,
  ) {
    derivedStateOf {
      name.isNotBlank() &&
        email.isNotBlank() &&
        password.isNotBlank() &&
        confirmPassword.isNotBlank() &&
        !isLoading &&
        !hasUsernameValidationError &&
        !hasEmailValidationError &&
        !hasPasswordValidationError &&
        !hasPasswordMatchError
    }
  }

  return RegisterValidationState(
    hasUsernameValidationError = hasUsernameValidationError,
    hasEmailValidationError = hasEmailValidationError,
    hasPasswordValidationError = hasPasswordValidationError,
    hasPasswordMatchError = hasPasswordMatchError,
    registrationEnabled = registrationEnabled,
  )
}

@Immutable
private data class RegisterValidationState(
  val hasUsernameValidationError: Boolean,
  val hasEmailValidationError: Boolean,
  val hasPasswordValidationError: Boolean,
  val hasPasswordMatchError: Boolean,
  val registrationEnabled: Boolean,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterTopBar(onBackClick: () -> Unit) {
  TopAppBar(
    title = {},
    navigationIcon = {
      IconButton(onClick = onBackClick) {
        Icon(
          imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
          contentDescription = null,
        )
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.background,
    ),
  )
}
