package com.example.indoorlocalizationcleancoders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.indoorlocalizationcleancoders.navigation.HeatmapReportView
import com.example.indoorlocalizationcleancoders.navigation.HomePage
import com.example.indoorlocalizationcleancoders.navigation.LoginPage
import com.example.indoorlocalizationcleancoders.navigation.RegistrationPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login"){
                        LoginPage(
                            navController = navController,
                            onLoginSuccessful = {
                                navController.navigate("home")
                            }
                        )
                    }
                    composable("home"){
                        HomePage(navController = navController)
                    }

                    composable ("register"){
                        RegistrationPage(
                            onRegistrationComplete = {
                                navController.navigate("login")
                            },
                            onNavigateToLogin = {
                                navController.navigate("login")
                            }
                        )
                    }
                    composable("heatmap"){
                        HeatmapReportView(navController = navController)
                    }
                }

            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
    }
}