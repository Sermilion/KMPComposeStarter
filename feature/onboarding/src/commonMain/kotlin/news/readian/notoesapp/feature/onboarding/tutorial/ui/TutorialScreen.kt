package news.readian.notoesapp.feature.onboarding.tutorial.ui

import androidx.compose.runtime.Composable
import news.readian.notoesapp.feature.onboarding.tutorial.TutorialViewModel

@Composable
expect fun TutorialScreen(viewModel: TutorialViewModel, onLoginOrRegisterClick: () -> Unit)

internal enum class TutorialPageType {
  Spotlight,
  Library,
  Discover,
}
