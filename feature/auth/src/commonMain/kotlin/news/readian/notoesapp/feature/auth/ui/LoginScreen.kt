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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import news.readian.notoesapp.core.designsystem.component.ButtonStyle
import news.readian.notoesapp.core.designsystem.component.ReadianButton
import news.readian.notoesapp.core.designsystem.component.ReadianPasswordField
import news.readian.notoesapp.core.designsystem.component.ReadianTextField
import news.readian.notoesapp.core.ui.composables.ErrorContainer
import news.readian.notoesapp.core.ui.composables.ErrorContent
import news.readian.notoesapp.feature.auth.testing.LoginTestIds
import news.readian.notoesapp.feature.auth.viewmodel.LoginContract
import news.readian.notoesapp.feature.auth.viewmodel.LoginViewModel
import notesapp.feature.auth.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
  viewModel: LoginViewModel,
  onForgotPasswordClick: () -> Unit,
  onBackClick: () -> Unit,
) {
  val uiState by viewModel.uiState.collectAsState()

  LoginScreen(
    uiState = uiState,
    navigation = object : LoginNavigation {
      override fun onForgotPasswordClick() = onForgotPasswordClick()
      override fun onLoginClick(email: String, password: String) {
        viewModel.onLogin(identifier = email, password = password)
      }
      override fun onBackClicked() = onBackClick()
    },
  )
}

@Suppress("LongMethod")
@Composable
internal fun LoginScreen(uiState: LoginContract.UiState, navigation: LoginNavigation) {
  val focusManager = LocalFocusManager.current

  Scaffold(
    topBar = { TopBar(onBackClick = navigation::onBackClicked) },
    contentWindowInsets = WindowInsets.statusBars,
  ) { innerPaddings ->
    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val loginButtonEnabled by remember(identifier, password) {
      derivedStateOf {
        identifier.isNotBlank() && password.isNotBlank()
      }
    }

    Box(
      modifier = Modifier
        .imePadding()
        .fillMaxSize()
        .padding(innerPaddings),
    ) {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        item {
          Text(
            text = stringResource(Res.string.label_login),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.testTag(LoginTestIds.LOGIN_TITLE),
          )
        }

        item {
          ErrorContainer(
            modifier = Modifier
              .height(96.dp)
              .testTag(LoginTestIds.ERROR_CONTAINER),
          ) {
            if (uiState.errors.isNotEmpty()) {
              RemoteValidationErrorContent(uiState.errors.toImmutableList())
            }
          }
        }

        item {
          ReadianTextField(
            value = identifier,
            label = stringResource(Res.string.label_email),
            onValueChange = { identifier = it },
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 32.dp)
              .testTag(LoginTestIds.EMAIL_FIELD),
          )
        }

        item {
          ReadianPasswordField(
            value = password,
            label = stringResource(Res.string.label_password),
            onValueChange = { password = it },
            modifier = Modifier
              .padding(top = 8.dp)
              .testTag(LoginTestIds.PASSWORD_FIELD),
            passwordVisible = passwordVisible,
            onPasswordVisibilityClick = { passwordVisible = !passwordVisible },
            passwordVisibilityTestTag = LoginTestIds.PASSWORD_VISIBILITY_TOGGLE,
            keyboardOptions = KeyboardOptions(
              imeAction = ImeAction.Done,
              autoCorrectEnabled = false,
              keyboardType = KeyboardType.Password,
            ),
            keyboardActions = KeyboardActions(
              onDone = {
                focusManager.clearFocus()
                navigation.onLoginClick(identifier, password)
              },
            ),
          )
        }

        item {
          ReadianButton(
            text = stringResource(Res.string.label_forgot_password),
            style = ButtonStyle.Text,
            onClick = navigation::onForgotPasswordClick,
            modifier = Modifier
              .align(Alignment.Center)
              .testTag(LoginTestIds.FORGOT_PASSWORD_BUTTON),
          )
        }

        item {
          ReadianButton(
            text = stringResource(Res.string.label_login),
            style = ButtonStyle.Primary,
            onClick = {
              focusManager.clearFocus()
              navigation.onLoginClick(identifier, password)
            },
            enabled = loginButtonEnabled,
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 32.dp)
              .padding(top = 16.dp)
              .testTag(LoginTestIds.LOGIN_BUTTON),
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
      if (uiState.loading) {
        CircularProgressIndicator(
          modifier = Modifier
            .align(Alignment.Center)
            .testTag(LoginTestIds.LOADING_INDICATOR),
        )
      }
    }
  }
}

@Composable
private fun RemoteValidationErrorContent(errors: ImmutableList<LoginContract.LoginProblem>) {
  val builder = StringBuilder()
  val errorFieldFormat = stringResource(Res.string.error_field_format)
  errors.forEach {
    when (it) {
      LoginContract.LoginProblem.InvalidCredentials -> {
        builder.append(stringResource(Res.string.error_invalid_credentials))
        builder.append("\n")
      }

      is LoginContract.LoginProblem.Validation -> {
        it.fields.forEach { field ->
          builder.append(errorFieldFormat.replace("%s", field.value()))
          builder.append("\n")
        }
      }

      LoginContract.LoginProblem.NetworkTimeout -> {
        builder.append(stringResource(Res.string.error_network_timeout))
        builder.append("\n")
      }

      LoginContract.LoginProblem.RateLimited -> {
        builder.append(stringResource(Res.string.error_rate_limited))
        builder.append("\n")
      }

      LoginContract.LoginProblem.ServerError -> {
        builder.append(stringResource(Res.string.error_server))
        builder.append("\n")
      }

      LoginContract.LoginProblem.GenericError -> {
        builder.append(stringResource(Res.string.error_generic))
        builder.append("\n")
      }
    }
  }
  ErrorContent(builder.toString())
}

@Composable
private fun LoginContract.Field.value(): String = stringResource(
  when (this) {
    LoginContract.Field.Identifier -> Res.string.label_identifier
    LoginContract.Field.Password -> Res.string.label_password
    LoginContract.Field.Unknown -> Res.string.label_unknown
  },
).lowercase()

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(onBackClick: () -> Unit) {
  TopAppBar(
    title = {
      // intentionally left empty
    },
    navigationIcon = {
      IconButton(
        onClick = onBackClick,
        modifier = Modifier.testTag(LoginTestIds.BACK_BUTTON),
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
          contentDescription = stringResource(Res.string.action_navigate_back),
        )
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.background,
    ),
  )
}

internal interface LoginNavigation {
  fun onForgotPasswordClick()
  fun onLoginClick(email: String, password: String)
  fun onBackClicked()
}
