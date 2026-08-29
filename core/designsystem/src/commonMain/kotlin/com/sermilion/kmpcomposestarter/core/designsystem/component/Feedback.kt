package com.sermilion.kmpcomposestarter.core.designsystem.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp

/**
 * An inline failure message.
 *
 * The polite live region is the point: an error that appears after a submit is text a sighted
 * user notices and a screen-reader user never hears unless the node announces itself.
 */
@Composable
fun StarterErrorText(
  text: String,
  modifier: Modifier = Modifier,
) {
  Text(
    text = text,
    color = MaterialTheme.colorScheme.error,
    style = MaterialTheme.typography.bodySmall,
    modifier = modifier.semantics { liveRegion = LiveRegionMode.Polite },
  )
}

/**
 * The spinner that goes *inside* a button while its action is in flight.
 *
 * Buttons keep their place and go disabled rather than being replaced by a bare indicator, so the
 * layout does not jump under the user's finger mid-tap.
 *
 * @param label the button's own label. The indicator replaces the button's `Text`, and without it
 *   the merged button node has a state but no name — a screen-reader user hears that something is
 *   busy and never learns which button it was.
 * @param stateDescription what a screen reader announces instead of silence; caller-supplied so it
 *   comes from a string resource rather than a literal here.
 */
@Composable
fun ButtonBusyIndicator(
  label: String,
  stateDescription: String,
  modifier: Modifier = Modifier,
) {
  CircularProgressIndicator(
    modifier =
      modifier
        .size(18.dp)
        .progressSemantics()
        .semantics {
          contentDescription = label
          this.stateDescription = stateDescription
        },
    color = LocalContentColor.current,
    strokeWidth = 2.dp,
  )
}
