package news.readian.notoesapp.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import news.readian.notoesapp.core.designsystem.theme.KmpTheme
import notesapp.core.designsystem.generated.resources.Res
import notesapp.core.designsystem.generated.resources.action_hide
import notesapp.core.designsystem.generated.resources.action_show
import org.jetbrains.compose.resources.stringResource

@Composable
fun ReadianTextField(
  value: String,
  label: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  trailingIcon: @Composable (() -> Unit)? = null,
  enabled: Boolean = true,
  isError: Boolean = false,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
  keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
  OutlinedTextField(
    modifier = Modifier
      .widthIn(max = 460.dp)
      .fillMaxWidth()
      .padding(horizontal = 32.dp)
      .then(modifier),
    value = value,
    enabled = enabled,
    isError = isError,
    maxLines = 1,
    singleLine = true,
    onValueChange = onValueChange,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    label = { Text(text = label) },
    visualTransformation = visualTransformation,
    trailingIcon = trailingIcon,
    colors = KmpTheme.outlinedTextFieldColors,
  )
}

@Composable
fun ReadianPasswordField(
  value: String,
  label: String,
  passwordVisible: Boolean,
  onValueChange: (String) -> Unit,
  onPasswordVisibilityClick: () -> Unit,
  modifier: Modifier = Modifier,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  keyboardOptions: KeyboardOptions = KeyboardOptions(
    imeAction = ImeAction.Go,
    autoCorrectEnabled = false,
    keyboardType = KeyboardType.Password,
  ),
  enabled: Boolean = true,
  isError: Boolean = false,
) {
  val toggleLabel = stringResource(
    if (passwordVisible) Res.string.action_hide else Res.string.action_show,
  )

  OutlinedTextField(
    modifier = Modifier
      .widthIn(max = 460.dp)
      .fillMaxWidth()
      .padding(horizontal = 32.dp)
      .then(modifier),
    value = value,
    enabled = enabled,
    isError = isError,
    maxLines = 1,
    singleLine = true,
    onValueChange = onValueChange,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    label = { Text(text = label) },
    visualTransformation = if (passwordVisible) {
      VisualTransformation.None
    } else {
      PasswordVisualTransformation()
    },
    trailingIcon = {
      Text(
        text = toggleLabel,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.clickable(onClick = onPasswordVisibilityClick),
      )
    },
    colors = KmpTheme.outlinedTextFieldColors,
  )
}
