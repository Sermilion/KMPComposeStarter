package com.sermilion.kmpcomposestarter.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.sermilion.kmpcomposestarter.core.designsystem.component.ButtonBusyIndicator
import com.sermilion.kmpcomposestarter.core.designsystem.component.StarterErrorText
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * The theme's own review surface.
 *
 * Both schemes are generated from one seed, so a regression in one role — a container that
 * collapses into its surface, an `on*` pairing that stops being readable — is only visible if
 * every role is rendered somewhere. These previews are that somewhere, and `check` compiles them.
 */
@Composable
private fun ColorSwatch(name: String, container: Color, content: Color) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Box(
      modifier = Modifier.size(40.dp).background(container),
      contentAlignment = Alignment.Center,
    ) {
      Text(text = "Aa", color = content, style = MaterialTheme.typography.labelSmall)
    }
    Text(
      text = name,
      style = MaterialTheme.typography.bodySmall,
      modifier = Modifier.padding(start = 12.dp),
    )
  }
}

private fun ColorScheme.reviewPairs(): List<Triple<String, Color, Color>> = listOf(
  Triple("primary", primary, onPrimary),
  Triple("primaryContainer", primaryContainer, onPrimaryContainer),
  Triple("secondary", secondary, onSecondary),
  Triple("secondaryContainer", secondaryContainer, onSecondaryContainer),
  Triple("tertiary", tertiary, onTertiary),
  Triple("tertiaryContainer", tertiaryContainer, onTertiaryContainer),
  Triple("error", error, onError),
  Triple("errorContainer", errorContainer, onErrorContainer),
  Triple("background", background, onBackground),
  Triple("surface", surface, onSurface),
  Triple("surfaceVariant", surfaceVariant, onSurfaceVariant),
  Triple("surfaceContainerLowest", surfaceContainerLowest, onSurface),
  Triple("surfaceContainerLow", surfaceContainerLow, onSurface),
  Triple("surfaceContainer", surfaceContainer, onSurface),
  Triple("surfaceContainerHigh", surfaceContainerHigh, onSurface),
  Triple("surfaceContainerHighest", surfaceContainerHighest, onSurface),
  Triple("inverseSurface", inverseSurface, inverseOnSurface),
  Triple("outline", outline, surface),
  Triple("outlineVariant", outlineVariant, onSurface),
)

@Composable
private fun ColorSchemeSheet(darkTheme: Boolean) {
  StarterTheme(darkTheme = darkTheme) {
    Surface {
      Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
        MaterialTheme.colorScheme.reviewPairs().forEach { (name, container, content) ->
          ColorSwatch(name = name, container = container, content = content)
        }
      }
    }
  }
}

@Preview
@Composable
private fun LightColorSchemePreview() = ColorSchemeSheet(darkTheme = false)

@Preview
@Composable
private fun DarkColorSchemePreview() = ColorSchemeSheet(darkTheme = true)

private fun Typography.reviewStyles(): List<Pair<String, TextStyle>> = listOf(
  "displaySmall" to displaySmall,
  "headlineLarge" to headlineLarge,
  "headlineSmall" to headlineSmall,
  "titleLarge" to titleLarge,
  "titleMedium" to titleMedium,
  "bodyLarge" to bodyLarge,
  "bodyMedium" to bodyMedium,
  "bodySmall" to bodySmall,
  "labelLarge" to labelLarge,
  "labelSmall" to labelSmall,
)

@Composable
private fun TypographySheet(darkTheme: Boolean) {
  StarterTheme(darkTheme = darkTheme) {
    Surface {
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
      ) {
        MaterialTheme.typography.reviewStyles().forEach { (name, style) ->
          Text(text = name, style = style)
        }
      }
    }
  }
}

@Preview
@Composable
private fun LightTypographyPreview() = TypographySheet(darkTheme = false)

@Preview
@Composable
private fun DarkTypographyPreview() = TypographySheet(darkTheme = true)

/**
 * The Material 3 components this app actually renders, in both schemes.
 *
 * The literals below are preview fixtures, not shipped UI copy: nothing in this file is reachable
 * from the running app, so they deliberately do not go through `composeResources`.
 */
@Composable
private fun ComponentSheet(darkTheme: Boolean) {
  StarterTheme(darkTheme = darkTheme) {
    Surface {
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Button") }
        Button(onClick = {}, modifier = Modifier.fillMaxWidth(), enabled = false) {
          ButtonBusyIndicator(stateDescription = "Working")
        }
        OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("OutlinedButton") }
        TextButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("TextButton") }
        OutlinedTextField(
          value = "typed value",
          onValueChange = {},
          label = { Text("OutlinedTextField") },
          modifier = Modifier.fillMaxWidth(),
        )
        StarterErrorText(text = "Something went wrong. Please try again.")
        SelectionBar()
      }
    }
  }
}

/** The selected item has to read as selected in both schemes; that is the regression it guards. */
@Composable
private fun SelectionBar() {
  NavigationBar {
    listOf(
      Triple("Home", Icons.Default.Home, true),
      Triple("Profile", Icons.Default.Person, false),
      Triple("Settings", Icons.Default.Settings, false),
    ).forEach { (label, icon, selected) ->
      NavigationBarItem(
        selected = selected,
        onClick = {},
        icon = { Icon(imageVector = icon, contentDescription = null) },
        label = { Text(label) },
      )
    }
  }
}

@Preview
@Composable
private fun LightComponentsPreview() = ComponentSheet(darkTheme = false)

@Preview
@Composable
private fun DarkComponentsPreview() = ComponentSheet(darkTheme = true)
