package com.sermilion.kmpcomposestarter.feature.auth.ui

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
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
  authRepository: AuthRepository,
  onNavigateToRegister: () -> Unit,
  onLoginSuccess: () -> Unit,
) {
  val scope = rememberCoroutineScope()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "Login Screen",
      style = MaterialTheme.typography.headlineLarge
    )

    Spacer(modifier = Modifier.height(32.dp))

    Button(
      onClick = {
        scope.launch {
          authRepository.login("test@test.com", "password")
          onLoginSuccess()
        }
      },
      modifier = Modifier.fillMaxWidth()
    ) {
      Text("Sign In (Demo)")
    }

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedButton(
      onClick = onNavigateToRegister,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text("Create Account")
    }
  }
}
