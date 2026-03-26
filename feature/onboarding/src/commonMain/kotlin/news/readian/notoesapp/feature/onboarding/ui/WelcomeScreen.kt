package news.readian.notoesapp.feature.onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import news.readian.notoesapp.core.designsystem.component.ButtonStyle
import news.readian.notoesapp.core.designsystem.component.ReadianButton
import news.readian.notoesapp.core.designsystem.icon.ReadianIcons
import news.readian.notoesapp.core.designsystem.icon.icons.ReadianLetter
import news.readian.notoesapp.core.designsystem.icon.icons.ReadianText
import news.readian.notoesapp.core.designsystem.icon.icons.ReadianWelcome
import news.readian.notoesapp.core.ui.composables.ErrorContainer
import news.readian.notoesapp.core.ui.composables.ErrorContent
import news.readian.notoesapp.core.ui.composables.LoadingContent
import news.readian.notoesapp.feature.onboarding.viewmodel.WelcomeContract
import notesapp.feature.onboarding.generated.resources.Res
import notesapp.feature.onboarding.generated.resources.error_generic
import notesapp.feature.onboarding.generated.resources.label_login
import notesapp.feature.onboarding.generated.resources.label_register
import notesapp.feature.onboarding.generated.resources.label_skip
import notesapp.feature.onboarding.generated.resources.readian_icon
import notesapp.feature.onboarding.generated.resources.welcome_image
import org.jetbrains.compose.resources.stringResource

@Composable
fun WelcomeScreen(
  uiState: WelcomeContract.UiState,
  onLoginClick: () -> Unit,
  onSignUpClick: () -> Unit,
  onSkipClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    contentWindowInsets = WindowInsets.statusBars,
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
    ) {
      WelcomeContent(
        uiState = uiState,
        onLoginClick = onLoginClick,
        onSignUpClick = onSignUpClick,
        onSkipClick = onSkipClick,
      )

      if (uiState.isLoading) {
        LoadingContent()
      }
    }
  }
}

@Composable
private fun WelcomeContent(
  uiState: WelcomeContract.UiState,
  onLoginClick: () -> Unit,
  onSignUpClick: () -> Unit,
  onSkipClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    LogoContent(modifier = Modifier.fillMaxWidth().padding(top = 24.dp))
    Spacer(modifier = Modifier.height(90.dp))
    WelcomeIllustration()

    ErrorContainer(modifier = Modifier.height(108.dp)) {
      if (uiState.hasError) {
        ErrorContent(stringResource(Res.string.error_generic))
      }
    }

    WelcomeActions(
      uiState = uiState,
      onLoginClick = onLoginClick,
      onSignUpClick = onSignUpClick,
      onSkipClick = onSkipClick,
    )
  }
}

@Composable
private fun WelcomeIllustration(modifier: Modifier = Modifier) {
  Image(
    imageVector = ReadianIcons.ReadianWelcome,
    contentDescription = stringResource(Res.string.welcome_image),
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp),
  )
}

@Composable
private fun WelcomeActions(
  uiState: WelcomeContract.UiState,
  onLoginClick: () -> Unit,
  onSignUpClick: () -> Unit,
  onSkipClick: () -> Unit,
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
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp),
    )

    ReadianButton(
      text = stringResource(Res.string.label_register),
      style = ButtonStyle.Secondary,
      onClick = onSignUpClick,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp)
        .padding(top = 2.dp),
    )

    ReadianButton(
      text = stringResource(Res.string.label_skip),
      enabled = !uiState.isLoading,
      style = ButtonStyle.Tertiary,
      onClick = onSkipClick,
      modifier = Modifier.padding(top = 2.dp),
    )
  }
}

@Composable
private fun LogoContent(modifier: Modifier = Modifier) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      imageVector = ReadianIcons.ReadianLetter,
      contentDescription = stringResource(Res.string.readian_icon),
      tint = MaterialTheme.colorScheme.primary,
    )

    Icon(
      imageVector = ReadianIcons.ReadianText,
      contentDescription = stringResource(Res.string.readian_icon),
      modifier = Modifier.padding(start = 8.dp),
      tint = MaterialTheme.colorScheme.tertiary,
    )
  }
}
