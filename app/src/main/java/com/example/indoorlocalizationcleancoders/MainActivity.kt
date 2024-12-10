package com.example.indoorlocalizationcleancoders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.indoorlocalizationcleancoders.components.BottomNavigationBar
import com.example.indoorlocalizationcleancoders.components.HeaderComponent
import hr.foi.air.heatmapreport.view.HeatmapReportView
import com.example.indoorlocalizationcleancoders.navigation.HomePage
import com.example.indoorlocalizationcleancoders.navigation.LoginPage
import com.example.indoorlocalizationcleancoders.navigation.RegistrationPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                val navController = rememberNavController()
                Scaffold(

                        topBar = {
                            HeaderComponent(
                                title = navController.currentBackStackEntry?.destination?.route ?: "",
                                onBackPressed = {
                                    navController.navigate("home")
                                }
                            )
                        },

                    bottomBar = {
                        if(!isLoginOrRegister(navController))
                        BottomNavigationBar(navController = navController)
                    }
                ) { padding ->
                    NavHost(navController = navController, startDestination = "login", modifier = Modifier.padding(padding)) {
                        composable("login") {
                            LoginPage(
                                navController = navController,
                                onLoginSuccessful = {
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                context = LocalContext.current
                            )
                        }
                        composable("home") {
                            HomePage(navController = navController)
                        }
                        composable("register") {
                            RegistrationPage(
                                navController = navController,
                                onRegistrationComplete = {
                                    navController.navigate("login") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                context = LocalContext.current
                            )
                        }
                        composable("heatmap") {
                            HeatmapReportView(navController = navController)
                        }
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

@Composable
fun isLoginOrRegister(navController: NavController): Boolean {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    return currentRoute == "login" || currentRoute == "register"
}