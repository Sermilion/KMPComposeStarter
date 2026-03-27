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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import news.readian.notoesapp.core.designsystem.component.ButtonStyle
import news.readian.notoesapp.core.designsystem.component.ReadianButton
import news.readian.notoesapp.core.designsystem.icon.ReadianIcons
import news.readian.notoesapp.core.designsystem.icon.icons.ReadianLetter
import news.readian.notoesapp.core.designsystem.icon.icons.ReadianText
import news.readian.notoesapp.core.designsystem.icon.icons.ReadianWelcome
import news.readian.notoesapp.core.ui.composables.ErrorContainer
import news.readian.notoesapp.core.ui.composables.LoadingContent
import news.readian.notoesapp.feature.onboarding.common.ui.RemoteValidationErrorContent
import news.readian.notoesapp.feature.onboarding.welcome.WelcomeContract
import news.readian.notoesapp.feature.onboarding.welcome.WelcomeViewModel
import notesapp.feature.onboarding.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun WelcomeScreen(
  onLoginClick: () -> Unit,
  onSignUpClick: () -> Unit,
  viewModel: WelcomeViewModel,
) {
  val uiState by viewModel.uiState.collectAsState()

  WelcomeScreen(
    onLoginClick = onLoginClick,
    onSignUpClick = onSignUpClick,
    uiState = uiState,
    onSkipClick = viewModel::onSkipClick,
  )
}

@Composable
private fun WelcomeScreen(
  onLoginClick: () -> Unit,
  onSignUpClick: () -> Unit,
  uiState: WelcomeContract.UiState,
  onSkipClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .then(modifier),
    contentWindowInsets = WindowInsets.statusBars,
  ) {
    Box(modifier = Modifier.fillMaxSize().padding(it)) {
      Column(
        modifier = Modifier
          .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        LogoContent()

        Spacer(modifier = Modifier.height(90.dp))

        Image(
          imageVector = ReadianIcons.ReadianWelcome,
          contentDescription = stringResource(Res.string.welcome_image),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        )

        ErrorContainer(modifier = Modifier.height(108.dp)) {
          if (uiState.errors.isNotEmpty()) {
            RemoteValidationErrorContent(uiState.errors.toImmutableList())
          }
        }

        ReadianButton(
          text = stringResource(Res.string.label_login),
          style = ButtonStyle.Primary,
          onClick = onLoginClick,
          modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
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
          enabled = !uiState.loading,
          style = ButtonStyle.Tertiary,
          onClick = onSkipClick,
          modifier = Modifier.padding(top = 2.dp),
        )
      }
      if (uiState.loading) {
        LoadingContent()
      }
    }
  }
}

@Composable
private fun LogoContent() {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 24.dp),
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
