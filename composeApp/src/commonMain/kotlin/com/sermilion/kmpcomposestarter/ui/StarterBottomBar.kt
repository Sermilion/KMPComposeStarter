package com.sermilion.kmpcomposestarter.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sermilion.kmpcomposestarter.navigation.TopLevelTab
import org.jetbrains.compose.resources.stringResource

@Composable
fun StarterBottomBar(
  currentTab: TopLevelTab,
  onTabSelected: (TopLevelTab) -> Unit,
  modifier: Modifier = Modifier,
) {
  NavigationBar(modifier = modifier) {
    TopLevelTab.entries.forEach { tab ->
      val label = stringResource(tab.label)
      NavigationBarItem(
        selected = currentTab == tab,
        onClick = { onTabSelected(tab) },
        // Null on purpose: the visible label below is already the item's announcement, and a
        // matching contentDescription made every tab announce its name twice.
        icon = { Icon(imageVector = tab.icon, contentDescription = null) },
        label = { Text(text = label) },
      )
    }
  }
}
