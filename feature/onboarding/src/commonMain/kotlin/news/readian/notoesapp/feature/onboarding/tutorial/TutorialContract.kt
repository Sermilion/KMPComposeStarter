package news.readian.notoesapp.feature.onboarding.tutorial

import news.readian.notoesapp.feature.onboarding.common.ui.model.AnonRegProblem

object TutorialContract {
  data class UiState(val loading: Boolean = false, val errors: List<AnonRegProblem> = listOf())
}
