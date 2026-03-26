package news.readian.notoesapp.feature.auth.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import news.readian.notoesapp.core.designsystem.component.ButtonStyle
import news.readian.notoesapp.core.designsystem.component.ReadianButton
import news.readian.notoesapp.core.designsystem.component.ReadianPasswordField
import news.readian.notoesapp.core.designsystem.component.ReadianTextField
import news.readian.notoesapp.core.ui.composables.ErrorContainer
import news.readian.notoesapp.core.ui.composables.ErrorContent
import news.readian.notoesapp.core.ui.composables.LoadingContent
import news.readian.notoesapp.feature.auth.viewmodel.LoginContract
import notesapp.feature.auth.generated.resources.Res
import notesapp.feature.auth.generated.resources.error_invalid_credentials
import notesapp.feature.auth.generated.resources.label_email
import notesapp.feature.auth.generated.resources.label_login
import notesapp.feature.auth.generated.resources.label_password
import notesapp.feature.auth.generated.resources.label_sign_up
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
  uiState: LoginContract.UiState,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onLoginClick: () -> Unit,
  onNavigateToRegister: () -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    topBar = { LoginTopBar(onBackClick = onBackClick) },
    contentWindowInsets = WindowInsets.statusBars,
  ) { innerPadding ->
    Box(
      modifier = modifier
        .imePadding()
        .fillMaxSize()
        .padding(innerPadding),
    ) {
      LoginForm(
        uiState = uiState,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onLoginClick = onLoginClick,
        onNavigateToRegister = onNavigateToRegister,
      )

      if (uiState.isLoading) {
        LoadingContent()
      }
    }
  }
}

@Composable
private fun LoginForm(
  uiState: LoginContract.UiState,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onLoginClick: () -> Unit,
  onNavigateToRegister: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val focusManager = LocalFocusManager.current
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  val loginButtonEnabled by remember(uiState.email, uiState.password, uiState.isLoading) {
    derivedStateOf {
      uiState.email.isNotBlank() && uiState.password.isNotBlank() && !uiState.isLoading
    }
  }

  LazyColumn(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    item { LoginHeader(uiState = uiState) }

    item {
      LoginCredentialsFields(
        uiState = uiState,
        passwordVisible = passwordVisible,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onPasswordVisibilityClick = { passwordVisible = !passwordVisible },
        onKeyboardDone = {
          focusManager.clearFocus()
          if (loginButtonEnabled) {
            onLoginClick()
          }
        },
      )
    }

    item {
      LoginActions(
        loginButtonEnabled = loginButtonEnabled,
        isLoading = uiState.isLoading,
        onLoginClick = {
          focusManager.clearFocus()
          onLoginClick()
        },
        onNavigateToRegister = onNavigateToRegister,
      )
    }

    item {
      AuthTermsView(
        modifier = Modifier
          .padding(horizontal = 80.dp)
          .padding(top = 32.dp, bottom = 16.dp),
      )
    }
  }
}

@Composable
private fun LoginHeader(uiState: LoginContract.UiState, modifier: Modifier = Modifier) {
  Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = stringResource(Res.string.label_login),
      style = MaterialTheme.typography.headlineMedium,
    )

    ErrorContainer(modifier = Modifier.height(96.dp)) {
      loginErrorMessage(uiState)?.let { ErrorContent(it) }
    }
  }
}

@Composable
private fun LoginCredentialsFields(
  uiState: LoginContract.UiState,
  passwordVisible: Boolean,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onPasswordVisibilityClick: () -> Unit,
  onKeyboardDone: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    ReadianTextField(
      value = uiState.email,
      label = stringResource(Res.string.label_email),
      onValueChange = onEmailChange,
      enabled = !uiState.isLoading,
      modifier = Modifier.padding(top = 32.dp),
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Email,
        autoCorrectEnabled = false,
      ),
    )

    ReadianPasswordField(
      value = uiState.password,
      label = stringResource(Res.string.label_password),
      passwordVisible = passwordVisible,
      onValueChange = onPasswordChange,
      onPasswordVisibilityClick = onPasswordVisibilityClick,
      modifier = Modifier.padding(top = 8.dp),
      enabled = !uiState.isLoading,
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
        autoCorrectEnabled = false,
        keyboardType = KeyboardType.Password,
      ),
      keyboardActions = KeyboardActions(onDone = { onKeyboardDone() }),
    )
  }
}

@Composable
private fun LoginActions(
  loginButtonEnabled: Boolean,
  isLoading: Boolean,
  onLoginClick: () -> Unit,
  onNavigateToRegister: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    ReadianButton(
      text = stringResource(Res.string.label_login),
      style = ButtonStyle.Primary,
      onClick = onLoginClick,
      enabled = loginButtonEnabled,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp)
        .padding(top = 16.dp),
    )

    ReadianButton(
      text = stringResource(Res.string.label_sign_up),
      style = ButtonStyle.Secondary,
      onClick = onNavigateToRegister,
      enabled = !isLoading,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp)
        .padding(top = 8.dp),
    )
  }
}

@Composable
private fun loginErrorMessage(uiState: LoginContract.UiState): String? {
  val error = uiState.error ?: return null
  return when (error) {
    LoginContract.Error.InvalidCredentials -> stringResource(Res.string.error_invalid_credentials)
    is LoginContract.Error.Unknown -> error.message.ifBlank {
      stringResource(Res.string.error_invalid_credentials)
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginTopBar(onBackClick: () -> Unit) {
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
