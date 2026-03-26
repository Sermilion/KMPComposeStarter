package news.readian.notoesapp.feature.onboarding.tutorial.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import news.readian.notoesapp.feature.onboarding.tutorial.TutorialViewModel

@Composable
actual fun TutorialScreen(viewModel: TutorialViewModel, onLoginOrRegisterClick: () -> Unit) {
  val uiState by viewModel.uiState.collectAsState()

  WebTutorialScreen(
    uiState = uiState,
    onNextClick = viewModel::onNext,
    onLoginOrRegisterClick = onLoginOrRegisterClick,
  )
}
