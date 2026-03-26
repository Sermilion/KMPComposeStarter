
package news.readian.notoesapp.feature.onboarding.common.ui.model

sealed interface AnonRegProblem {
  data object GenericError : AnonRegProblem
}
