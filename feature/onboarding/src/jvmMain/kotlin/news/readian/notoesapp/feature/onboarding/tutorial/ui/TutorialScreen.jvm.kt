package news.readian.notoesapp.feature.onboarding.tutorial.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import news.readian.notoesapp.common.compose.collectAsStateMultiplatform
import news.readian.notoesapp.feature.onboarding.tutorial.TutorialViewModel

@Composable
actual fun TutorialScreen(viewModel: TutorialViewModel, onLoginOrRegisterClick: () -> Unit) {
  val uiState by viewModel.uiState.collectAsStateMultiplatform()

  WebTutorialScreen(
    uiState = uiState,
    onNextClick = viewModel::onNext,
    onLoginOrRegisterClick = onLoginOrRegisterClick,
  )
}
