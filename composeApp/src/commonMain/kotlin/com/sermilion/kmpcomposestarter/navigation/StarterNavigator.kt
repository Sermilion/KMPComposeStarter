package com.sermilion.kmpcomposestarter.navigation

import androidx.compose.runtime.MutableState
import com.sermilion.kmpcomposestarter.common.navigation.AuthFlowRoute
import com.sermilion.kmpcomposestarter.common.navigation.Route
import com.sermilion.kmpcomposestarter.common.navigation.TopLevelRoute
import com.sermilion.kmpcomposestarter.feature.auth.navigation.LoginRoute

class StarterNavigator(private val state: MutableState<StarterNavigationState>) {

  fun navigate(route: Route) {
    val currentState = state.value
    when {
      !currentState.isAuthenticated && route is AuthFlowRoute -> {
        val updatedStack = currentState.authBackStack.toMutableList()
        updatedStack.add(route)
        state.value = currentState.copy(authBackStack = updatedStack.toSnapshotStateList())
      }
      currentState.isAuthenticated && route is TopLevelRoute -> {
        val currentTab = currentState.currentTab
        val currentStack =
          currentState.tabBackStacks[currentTab]?.toMutableList() ?: mutableListOf()
        currentStack.add(route)
        val updatedTabStacks = currentState.tabBackStacks.toMutableMap()
        updatedTabStacks[currentTab] = currentStack.toSnapshotStateList()
        state.value = currentState.copy(tabBackStacks = updatedTabStacks)
      }
    }
  }

  fun navigateToTopLevel(tab: TopLevelTab) {
    val currentState = state.value
    if (currentState.currentTab == tab) {
      val currentStack = currentState.tabBackStacks[tab]
      if (currentStack != null && currentStack.size > 1) {
        val updatedTabStacks = currentState.tabBackStacks.toMutableMap()
        updatedTabStacks[tab] = listOf(tab.startRoute).toSnapshotStateList()
        state.value = currentState.copy(tabBackStacks = updatedTabStacks)
      }
    } else {
      state.value = currentState.copy(currentTab = tab)
    }
  }

  fun goBack(): Boolean {
    val currentState = state.value
    return if (currentState.isAuthenticated) {
      goBackInTab(currentState)
    } else {
      goBackInAuth(currentState)
    }
  }

  private fun goBackInTab(currentState: StarterNavigationState): Boolean {
    val currentTab = currentState.currentTab
    val currentStack = currentState.tabBackStacks[currentTab] ?: return false

    return if (currentStack.size > 1) {
      val updatedStack = currentStack.toMutableList()
      updatedStack.removeLastOrNull()
      val updatedTabStacks = currentState.tabBackStacks.toMutableMap()
      updatedTabStacks[currentTab] = updatedStack.toSnapshotStateList()
      state.value = currentState.copy(tabBackStacks = updatedTabStacks)
      true
    } else {
      false
    }
  }

  private fun goBackInAuth(currentState: StarterNavigationState): Boolean =
    if (currentState.authBackStack.size >
      1
    ) {
      val updatedStack = currentState.authBackStack.toMutableList()
      updatedStack.removeLastOrNull()
      state.value = currentState.copy(authBackStack = updatedStack.toSnapshotStateList())
      true
    } else {
      false
    }

  fun onLoginStateChanged(isLoggedIn: Boolean) {
    val currentState = state.value
    if (isLoggedIn) {
      val freshTabStacks = TopLevelTab.entries.associateWith {
        listOf(it.startRoute).toSnapshotStateList()
      }
      state.value = currentState.copy(
        isAuthenticated = true,
        tabBackStacks = freshTabStacks,
        currentTab = TopLevelTab.HOME,
      )
    } else {
      state.value = currentState.copy(
        isAuthenticated = false,
        authBackStack = listOf<AuthFlowRoute>(LoginRoute).toSnapshotStateList(),
      )
    }
  }
}
