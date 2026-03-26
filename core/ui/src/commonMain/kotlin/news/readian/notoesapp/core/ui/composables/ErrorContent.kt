package news.readian.notoesapp.core.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ErrorContent(error: String, modifier: Modifier = Modifier) {
  Text(
    text = error,
    modifier = Modifier
      .fillMaxWidth()
      .then(modifier),
    color = MaterialTheme.colorScheme.error,
    textAlign = TextAlign.Center,
    maxLines = 4,
    overflow = TextOverflow.Ellipsis,
  )
}

@Composable
fun ErrorContainer(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 8.dp)
      .padding(horizontal = 16.dp)
      .then(modifier),
    contentAlignment = Alignment.Center,
  ) {
    content()
  }
}
