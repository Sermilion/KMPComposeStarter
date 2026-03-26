package news.readian.notoesapp.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import news.readian.notoesapp.core.designsystem.theme.KmpContentAlpha
import news.readian.notoesapp.core.designsystem.theme.ReadianBlueDisabled

@Composable
fun ReadianButton(
  text: String,
  style: ButtonStyle,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
) {
  when (style) {
    ButtonStyle.Primary -> BaseButton(
      text = text,
      onClick = onClick,
      modifier = modifier,
      enabled = enabled,
      colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        disabledContainerColor = ReadianBlueDisabled,
      ),
    )
    ButtonStyle.Secondary -> BaseButton(
      text = text,
      onClick = onClick,
      modifier = modifier,
      enabled = enabled,
      textColor = MaterialTheme.colorScheme.primary,
      border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
      colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.background,
        disabledContainerColor = MaterialTheme.colorScheme.background,
      ),
    )
    ButtonStyle.Tertiary -> TextButton(
      onClick = onClick,
      modifier = modifier,
      enabled = enabled,
    ) {
      Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
      )
    }
    ButtonStyle.Text -> TextButton(
      onClick = onClick,
      modifier = modifier,
      enabled = enabled,
    ) {
      Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primaryContainer.copy(
          alpha = if (enabled) KmpContentAlpha.highEmphasis else KmpContentAlpha.lowEmphasis,
        ),
      )
    }
  }
}

@Composable
private fun BaseButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  textColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Unspecified,
  colors: androidx.compose.material3.ButtonColors = ButtonDefaults.buttonColors(),
  border: BorderStroke? = null,
) {
  Button(
    onClick = onClick,
    modifier = Modifier
      .widthIn(max = 460.dp)
      .then(modifier),
    enabled = enabled,
    border = border,
    shape = MaterialTheme.shapes.small,
    colors = colors,
  ) {
    Text(
      text = text,
      style = MaterialTheme.typography.bodyLarge,
      color = textColor,
    )
  }
}

enum class ButtonStyle {
  Primary,
  Secondary,
  Tertiary,
  Text,
}
