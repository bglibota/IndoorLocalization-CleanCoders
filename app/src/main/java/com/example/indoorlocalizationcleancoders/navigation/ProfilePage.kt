package com.example.indoorlocalizationcleancoders.navigation

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProfilePage(context: Context, navController: NavController) {
    val sharedPreferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)


    val userName = sharedPreferences.getString("userName", "No name") ?: "No name"
    val userUsername = sharedPreferences.getString("userUsername", "Unknown") ?: "Unknown"
    val userRoleId = sharedPreferences.getInt("userRoleId", 0)

    val role = when (userRoleId) {
        1 -> "Administrator"
        2 -> "User"
        else -> "Unknown role"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Username: $userUsername")
        Text(text = "Name: $userName")
        Text(text = "Role: $role")

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()


            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }) {
            Text("Log out")
        }
    }



}