package com.sermilion.kmpcomposestarter.feature.auth.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

/**
 * One text field for both auth forms.
 *
 * It exists so the keyboard type, IME action and autofill content type travel with the field
 * rather than being re-declared — and quietly forgotten — at each of the five call sites.
 */
@Composable
internal fun AuthFormField(
  value: String,
  onValueChange: (String) -> Unit,
  label: String,
  contentType: ContentType,
  keyboardType: KeyboardType,
  imeAction: ImeAction,
  enabled: Boolean,
  modifier: Modifier = Modifier,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(label) },
    modifier =
      modifier
        .fillMaxWidth()
        .semantics { this.contentType = contentType },
    singleLine = true,
    enabled = enabled,
    visualTransformation = visualTransformation,
    keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
    keyboardActions = keyboardActions,
  )
}
