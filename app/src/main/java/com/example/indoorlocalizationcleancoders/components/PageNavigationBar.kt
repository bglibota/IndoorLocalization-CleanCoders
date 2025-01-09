package com.example.indoorlocalizationcleancoders.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.indoorlocalizationcleancoders.R

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        BottomNavigationItem(
            icon = { Icon(painter = painterResource(id = R.drawable.home_icon), contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate("home") }
        )
        BottomNavigationItem(
            icon = { Icon(painter = painterResource(id = R.drawable.baseline_map_24), contentDescription = "Heatmap") },
            label = { Text("Heatmap") },
            selected = false,
            onClick = { navController.navigate("heatmap") }
        )
        BottomNavigationItem(
            icon = { Icon(painter = painterResource(id = R.drawable.baseline_add_24), contentDescription = "Zones") },
            label = { Text("Zones") },
            selected = false,
            onClick = { navController.navigate("zones") }
        )
        BottomNavigationItem(
            icon = { Icon(painter = painterResource(id = R.drawable.report), contentDescription = "Reports") },
            label = { Text("Reports") },
            selected = false,
            onClick = { navController.navigate("report") }
        )
    }
}
