package news.readian.notoesapp.feature.auth.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import news.readian.notoesapp.core.designsystem.theme.KmpContentAlpha
import notesapp.feature.auth.generated.resources.Res
import notesapp.feature.auth.generated.resources.label_and
import notesapp.feature.auth.generated.resources.label_by_joining
import notesapp.feature.auth.generated.resources.label_privacy_policy
import notesapp.feature.auth.generated.resources.label_terms_of_service
import org.jetbrains.compose.resources.stringResource

private const val PRIVACY_LINK = "https://readian.io/privacy"
private const val TERMS_LINK = "https://readian.io/terms"
private const val WHITE_SPACE = " "

@Composable
fun AuthTermsView(modifier: Modifier = Modifier) {
  val uriHandler = LocalUriHandler.current
  val textColor = MaterialTheme.colorScheme.onSurface.copy(alpha = KmpContentAlpha.lowEmphasis)
  val linkColor = MaterialTheme.colorScheme.onSurface

  val annotatedString = buildAnnotatedString {
    withStyle(style = SpanStyle(color = textColor)) {
      append(stringResource(Res.string.label_by_joining) + WHITE_SPACE)
    }

    withLink(
      LinkAnnotation.Clickable(
        tag = "privacy-policy",
        linkInteractionListener = { uriHandler.openUri(PRIVACY_LINK) },
      ),
    ) {
      withStyle(style = SpanStyle(color = linkColor)) {
        append(stringResource(Res.string.label_privacy_policy))
      }
    }

    withStyle(style = SpanStyle(color = textColor)) {
      append(WHITE_SPACE + stringResource(Res.string.label_and) + WHITE_SPACE)
    }

    withLink(
      LinkAnnotation.Clickable(
        tag = "terms-of-service",
        linkInteractionListener = { uriHandler.openUri(TERMS_LINK) },
      ),
    ) {
      withStyle(style = SpanStyle(color = linkColor)) {
        append(stringResource(Res.string.label_terms_of_service))
      }
    }
  }

  Text(
    text = annotatedString,
    style = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Center),
    modifier = modifier,
  )
}
