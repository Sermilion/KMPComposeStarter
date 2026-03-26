
package news.readian.notoesapp.feature.onboarding.welcome

import news.readian.notoesapp.feature.onboarding.common.ui.model.AnonRegProblem

object WelcomeContract {
  data class UiState(val loading: Boolean = false, val errors: List<AnonRegProblem> = listOf())
}
