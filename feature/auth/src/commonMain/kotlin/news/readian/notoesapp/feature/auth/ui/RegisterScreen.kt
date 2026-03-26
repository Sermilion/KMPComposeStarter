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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import news.readian.notoesapp.common.isValidEmail
import news.readian.notoesapp.common.isValidPassword
import news.readian.notoesapp.core.designsystem.component.ButtonStyle
import news.readian.notoesapp.core.designsystem.component.ReadianButton
import news.readian.notoesapp.core.designsystem.component.ReadianPasswordField
import news.readian.notoesapp.core.designsystem.component.ReadianTextField
import news.readian.notoesapp.core.ui.composables.ErrorContainer
import news.readian.notoesapp.core.ui.composables.ErrorContent
import news.readian.notoesapp.core.ui.composables.LoadingContent
import news.readian.notoesapp.feature.auth.testing.RegistrationTestIds
import news.readian.notoesapp.feature.auth.viewmodel.RegisterContract
import news.readian.notoesapp.feature.auth.viewmodel.RegisterContract.NavigationState
import news.readian.notoesapp.feature.auth.viewmodel.RegisterContract.RegistrationProblem
import news.readian.notoesapp.feature.auth.viewmodel.RegisterContract.RegistrationProblem.FieldValidation
import news.readian.notoesapp.feature.auth.viewmodel.RegisterContract.UiState
import news.readian.notoesapp.feature.auth.viewmodel.RegisterViewModel
import notesapp.feature.auth.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, onBackClick: () -> Unit) {
  val uiState by viewModel.uiState.collectAsState()
  val navigationState by viewModel.navigationState.collectAsState()

  LaunchedEffect(navigationState, onBackClick) {
    when (navigationState) {
      NavigationState.Close -> onBackClick()
      NavigationState.Registration -> Unit
    }
  }

  RegisterScreen(
    navigation = object : Navigation {
      override fun onSignUpClick(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
      ) {
        viewModel.onSignUpClick(
          username = username,
          email = email,
          password = password,
          confirmPassword = confirmPassword,
        )
      }

      override fun onBackClicked() = onBackClick()
    },
    uiState = uiState,
  )
}

@Composable
internal fun RegisterScreen(navigation: Navigation, uiState: UiState) {
  Scaffold(
    topBar = { TopBar(onBackClick = navigation::onBackClicked) },
    contentWindowInsets = WindowInsets.statusBars,
  ) { innerPaddings ->
    Box(
      modifier = Modifier
        .imePadding()
        .fillMaxSize()
        .padding(innerPaddings),
    ) {
      when (uiState) {
        is UiState.Initial -> LoadingContent()

        is UiState.Content -> RegisterContent(
          uiState = uiState,
          navigation = navigation,
        )
      }
    }
  }
}

@Suppress("LongMethod", "CyclomaticComplexMethod")
@Composable
private fun RegisterContent(
  uiState: UiState.Content,
  navigation: Navigation,
  modifier: Modifier = Modifier,
) {
  var username by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }
  var confirmPassword by remember { mutableStateOf("") }
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

  val hasUsernameValidationError by remember(username) {
    derivedStateOf { username.length < 2 && username.isNotBlank() }
  }
  val hasEmailError by remember(email) {
    derivedStateOf { email.isValidEmail().not() && email.isNotBlank() }
  }
  val hasPasswordValidationError by remember(password) {
    derivedStateOf { password.isValidPassword().not() && password.isNotBlank() }
  }
  val hasPasswordMatchError by remember(password) {
    derivedStateOf { password != confirmPassword }
  }

  val serverErrorFields = remember(uiState.errors) {
    uiState.errors
      .filterIsInstance<FieldValidation>()
      .flatMap { it.fields }
      .toSet()
  }
  val hasServerUsernameError = RegisterContract.Field.Username in serverErrorFields
  val hasServerEmailError = RegisterContract.Field.Email in serverErrorFields
  val hasServerPasswordError = RegisterContract.Field.Password in serverErrorFields

  val registrationButtonEnabled by remember(username, email, password, confirmPassword) {
    derivedStateOf {
      username.isNotBlank() &&
        email.isNotBlank() &&
        password.isNotBlank() &&
        confirmPassword.isNotBlank()
    }
  }

  val hasError by remember(
    hasEmailError,
    password,
    confirmPassword,
    hasUsernameValidationError,
    hasPasswordValidationError,
    hasPasswordMatchError,
  ) {
    derivedStateOf {
      hasUsernameValidationError ||
        hasEmailError ||
        hasPasswordValidationError ||
        password.isValidPassword().not() ||
        confirmPassword.isValidPassword().not() ||
        hasPasswordMatchError
    }
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag(RegistrationTestIds.REGISTRATION_FORM)
      .then(modifier),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    item { ScreenLabel() }

    item {
      ErrorsContent(
        hasPasswordValidationError = hasPasswordValidationError,
        uiState = uiState,
        hasEmailError = hasEmailError,
        hasUsernameValidationError = hasUsernameValidationError,
      )
    }

    item {
      ReadianTextField(
        value = username,
        isError = hasUsernameValidationError || hasServerUsernameError,
        label = stringResource(Res.string.label_username),
        onValueChange = { username = it },
        modifier = Modifier
          .padding(top = 8.dp)
          .testTag(RegistrationTestIds.USERNAME_FIELD),
      )
    }

    item {
      ReadianTextField(
        value = email,
        label = stringResource(Res.string.label_email),
        isError = hasEmailError || hasServerEmailError,
        onValueChange = { email = it },
        modifier = Modifier
          .padding(top = 8.dp)
          .testTag(RegistrationTestIds.EMAIL_FIELD),
      )
    }

    item {
      ReadianPasswordField(
        value = password,
        isError = hasPasswordValidationError || hasServerPasswordError,
        label = stringResource(Res.string.label_password),
        onValueChange = { password = it },
        modifier = Modifier
          .padding(top = 8.dp)
          .testTag(RegistrationTestIds.PASSWORD_FIELD),
        passwordVisible = passwordVisible,
        onPasswordVisibilityClick = { passwordVisible = !passwordVisible },
        passwordVisibilityTestTag = RegistrationTestIds.PASSWORD_VISIBILITY_TOGGLE,
      )
    }

    item {
      ReadianPasswordField(
        value = confirmPassword,
        isError = hasPasswordValidationError || hasPasswordMatchError,
        label = stringResource(Res.string.label_confirm_password),
        onValueChange = { confirmPassword = it },
        modifier = Modifier
          .padding(top = 8.dp)
          .testTag(RegistrationTestIds.CONFIRM_PASSWORD_FIELD),
        passwordVisible = confirmPasswordVisible,
        onPasswordVisibilityClick = {
          confirmPasswordVisible = !confirmPasswordVisible
        },
        passwordVisibilityTestTag = RegistrationTestIds.CONFIRM_PASSWORD_VISIBILITY_TOGGLE,
      )
    }

    item {
      ReadianButton(
        text = stringResource(Res.string.label_register),
        enabled = registrationButtonEnabled && !hasError,
        style = ButtonStyle.Primary,
        onClick = {
          navigation.onSignUpClick(
            username,
            email,
            password,
            confirmPassword,
          )
        },
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 32.dp)
          .padding(top = 16.dp)
          .testTag(RegistrationTestIds.REGISTER_BUTTON),
      )
    }

    item {
      AuthTermsView(
        modifier = Modifier
          .padding(horizontal = 80.dp)
          .padding(top = 92.dp),
      )
    }
  }

  if (uiState.loading) {
    LoadingContent(modifier = Modifier.testTag(RegistrationTestIds.LOADING_INDICATOR))
  }
}

@Composable
private fun ErrorsContent(
  hasPasswordValidationError: Boolean,
  uiState: UiState.Content,
  hasEmailError: Boolean,
  hasUsernameValidationError: Boolean,
) {
  ErrorContainer(
    modifier = Modifier
      .height(96.dp)
      .testTag(RegistrationTestIds.ERROR_CONTAINER),
  ) {
    val errors = when {
      hasPasswordValidationError -> {
        uiState.errors + FieldValidation(listOf(RegisterContract.Field.Password))
      }

      hasEmailError -> {
        uiState.errors + FieldValidation(listOf(RegisterContract.Field.EmailFormat))
      }

      hasUsernameValidationError -> {
        uiState.errors + FieldValidation(listOf(RegisterContract.Field.UsernameFormat))
      }

      else -> uiState.errors
    }
    RemoteValidationErrorContent(errors.toImmutableList())
  }
}

@Composable
private fun RemoteValidationErrorContent(errors: ImmutableList<RegistrationProblem>) {
  val builder = StringBuilder()
  errors.forEach {
    when (it) {
      RegistrationProblem.GenericError -> {
        builder.append(stringResource(Res.string.error_generic))
        builder.append("\n")
      }

      is FieldValidation -> it.mapToBuilder(builder)
    }
  }
  ErrorContent(builder.toString())
}

@Composable
private fun FieldValidation.mapToBuilder(builder: StringBuilder): StringBuilder {
  this.fields.forEach { field ->
    when (field) {
      RegisterContract.Field.Email -> {
        builder.append(stringResource(Res.string.error_email_exists))
        builder.append("\n")
      }

      RegisterContract.Field.UsernameFormat -> {
        builder.append(stringResource(Res.string.error_username_validation))
        builder.append("")
      }

      RegisterContract.Field.EmailFormat -> {
        builder.append(stringResource(Res.string.error_email_validation))
        builder.append("")
      }

      RegisterContract.Field.Username -> {
        builder.append(stringResource(Res.string.error_unique_username))
        builder.append("\n")
      }

      RegisterContract.Field.Password -> {
        builder.append(stringResource(Res.string.error_password_validation))
        builder.append("\n")
      }

      RegisterContract.Field.Unknown -> {
        builder.append(stringResource(Res.string.error_generic))
        builder.append("\n")
      }
    }
  }
  return builder
}

@Composable
private fun ScreenLabel() {
  Text(
    text = stringResource(Res.string.label_sign_up),
    style = MaterialTheme.typography.headlineMedium,
    modifier = Modifier.testTag(RegistrationTestIds.REGISTRATION_TITLE),
  )
}

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
        modifier = Modifier.testTag(RegistrationTestIds.BACK_BUTTON),
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

internal interface Navigation {
  fun onSignUpClick(username: String, email: String, password: String, confirmPassword: String)

  fun onBackClicked()
}
