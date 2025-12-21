package com.sermilion.kmpcomposestarter.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.sermilion.kmpcomposestarter.navigation.TopLevelTab
import kmpcomposestarter.composeapp.generated.resources.Res
import kmpcomposestarter.composeapp.generated.resources.tab_home
import kmpcomposestarter.composeapp.generated.resources.tab_profile
import kmpcomposestarter.composeapp.generated.resources.tab_settings
import org.jetbrains.compose.resources.stringResource

@Composable
fun StarterBottomBar(
  currentTab: TopLevelTab,
  onTabSelected: (TopLevelTab) -> Unit,
  modifier: Modifier = Modifier,
) {
  NavigationBar(modifier = modifier) {
    TopLevelTab.entries.forEach { tab ->
      val label = tab.labelRes()
      NavigationBarItem(
        selected = currentTab == tab,
        onClick = { onTabSelected(tab) },
        icon = { Icon(imageVector = tab.icon, contentDescription = label) },
        label = { Text(text = label) },
      )
    }
  }
}

private val TopLevelTab.icon: ImageVector
  get() = when (this) {
    TopLevelTab.HOME -> Icons.Default.Home
    TopLevelTab.PROFILE -> Icons.Default.Person
    TopLevelTab.SETTINGS -> Icons.Default.Settings
  }

@Composable
private fun TopLevelTab.labelRes(): String = when (this) {
  TopLevelTab.HOME -> stringResource(Res.string.tab_home)
  TopLevelTab.PROFILE -> stringResource(Res.string.tab_profile)
  TopLevelTab.SETTINGS -> stringResource(Res.string.tab_settings)
}
