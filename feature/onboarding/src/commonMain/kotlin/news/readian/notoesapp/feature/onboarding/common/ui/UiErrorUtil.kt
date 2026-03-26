
package news.readian.notoesapp.feature.onboarding.common.ui

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList
import news.readian.notoesapp.core.ui.composables.ErrorContent
import news.readian.notoesapp.feature.onboarding.common.ui.model.AnonRegProblem
import notesapp.feature.onboarding.generated.resources.Res
import notesapp.feature.onboarding.generated.resources.error_generic
import org.jetbrains.compose.resources.stringResource

@Composable
fun RemoteValidationErrorContent(errors: ImmutableList<AnonRegProblem>) {
  val builder = StringBuilder()
  errors.forEach {
    when (it) {
      AnonRegProblem.GenericError -> {
        builder.append(stringResource(Res.string.error_generic))
        builder.append("\n")
      }
    }
  }
  ErrorContent(builder.toString())
}
