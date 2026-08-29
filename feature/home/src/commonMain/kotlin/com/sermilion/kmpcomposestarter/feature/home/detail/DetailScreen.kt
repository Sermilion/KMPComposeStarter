package com.sermilion.kmpcomposestarter.feature.home.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sermilion.kmpcomposestarter.core.designsystem.theme.StarterTheme
import kmpcomposestarter.feature.home.generated.resources.Res
import kmpcomposestarter.feature.home.generated.resources.detail_button_back
import kmpcomposestarter.feature.home.generated.resources.detail_id_label
import kmpcomposestarter.feature.home.generated.resources.detail_note_label
import kmpcomposestarter.feature.home.generated.resources.detail_screen_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DetailScreen(
  uiState: DetailContract.UiState,
  onNoteChange: (String) -> Unit,
  onNavigateBack: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier =
      modifier
        .fillMaxSize()
        .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      text = stringResource(Res.string.detail_screen_title),
      style = MaterialTheme.typography.headlineLarge,
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = stringResource(Res.string.detail_id_label, uiState.id),
      style = MaterialTheme.typography.bodyLarge,
    )

    Spacer(modifier = Modifier.height(24.dp))

    OutlinedTextField(
      value = uiState.note,
      onValueChange = onNoteChange,
      label = { Text(stringResource(Res.string.detail_note_label)) },
      modifier = Modifier.fillMaxWidth(),
      singleLine = true,
    )

    Spacer(modifier = Modifier.height(16.dp))

    TextButton(onClick = onNavigateBack) {
      Text(stringResource(Res.string.detail_button_back))
    }
  }
}

private val sampleDetailState = DetailContract.UiState(id = "item-1", note = "Remember this")

@Composable
private fun DetailScreenPreviewHost(darkTheme: Boolean) {
  StarterTheme(darkTheme = darkTheme) {
    DetailScreen(uiState = sampleDetailState, onNoteChange = {}, onNavigateBack = {})
  }
}

@Preview
@Composable
internal fun DetailScreenLightPreview() = DetailScreenPreviewHost(darkTheme = false)

@Preview
@Composable
internal fun DetailScreenDarkPreview() = DetailScreenPreviewHost(darkTheme = true)
