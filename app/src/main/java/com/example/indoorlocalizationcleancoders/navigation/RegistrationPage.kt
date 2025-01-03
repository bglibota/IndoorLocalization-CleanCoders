package com.example.indoorlocalizationcleancoders.navigation

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.indoorlocalizationcleancoders.viewmodel.AuthViewModel

@Composable
fun RegistrationPage(navController: NavController,
    onRegistrationComplete: () -> Unit,
    context: Context
) {
    val authViewModel: AuthViewModel = viewModel()
    val registrationStatus = authViewModel.registrationStatus.value

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isRegistering by remember { mutableStateOf(false) }


    LaunchedEffect(registrationStatus) {
        if (registrationStatus == "Registration successful") {
            onRegistrationComplete()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Registration", style = MaterialTheme.typography.headlineMedium)

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (isRegistering) {
                    return@Button
                }

                if (name.isBlank() || username.isBlank() || password.isBlank()) {
                    errorMessage = "All fields are required"
                } else if (password.length < 6) {
                    errorMessage = "Password must be at least 6 characters"
                } else {
                    errorMessage = ""
                    isRegistering = true
                    authViewModel.register(context, name, username, password)

                    if (authViewModel.registrationStatus.value == "Registration successful") {
                    } else {
                        errorMessage = authViewModel.registrationStatus.value
                        isRegistering = false
                    }
                }
            },
            enabled = !isRegistering
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (registrationStatus.isNotEmpty()) {
            Text(text = registrationStatus)
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text("Already have an account? Login")
        }
    }
}