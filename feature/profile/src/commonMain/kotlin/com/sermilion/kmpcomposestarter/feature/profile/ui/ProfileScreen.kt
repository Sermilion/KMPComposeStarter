package com.sermilion.kmpcomposestarter.feature.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sermilion.kmpcomposestarter.core.data.repository.AuthRepository
import com.sermilion.kmpcomposestarter.core.domain.di.UserDependencies
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
  authRepository: AuthRepository,
  userComponent: UserDependencies?,
  onNavigateBack: () -> Unit,
  onLogout: () -> Unit,
) {
  val scope = rememberCoroutineScope()
  val userData = userComponent?.userData

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "Profile Screen",
      style = MaterialTheme.typography.headlineLarge
    )

    Spacer(modifier = Modifier.height(16.dp))

    userData?.let { user ->
      Text(
        text = "Name: ${user.name}",
        style = MaterialTheme.typography.bodyLarge
      )
      Text(
        text = "Email: ${user.email}",
        style = MaterialTheme.typography.bodyMedium
      )
    }

    Spacer(modifier = Modifier.height(32.dp))

    OutlinedButton(
      onClick = onNavigateBack,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text("Back to Home")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
      onClick = {
        scope.launch {
          authRepository.logout()
          onLogout()
        }
      },
      modifier = Modifier.fillMaxWidth()
    ) {
      Text("Logout")
    }
  }
}
