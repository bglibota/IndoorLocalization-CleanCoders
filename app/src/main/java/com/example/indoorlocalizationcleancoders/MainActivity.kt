package com.example.indoorlocalizationcleancoders

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.indoorlocalizationcleancoders.components.BottomNavigationBar
import com.example.indoorlocalizationcleancoders.components.HeaderComponent
import com.example.indoorlocalizationcleancoders.navigation.HeatmapPage
import com.example.indoorlocalizationcleancoders.navigation.HomePage
import com.example.indoorlocalizationcleancoders.navigation.LoginPage
import com.example.indoorlocalizationcleancoders.navigation.RegistrationPage
import com.example.indoorlocalizationcleancoders.navigation.ReportPage
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVMFactory
import hr.foi.air.heatmapreport.view.Views.Heatmap.MainHeatmapReportView
import hr.foi.air.heatmapreport.view.Views.Heatmap.TrackedObjectDetailsView
import hr.foi.air.heatmapreport.view.Views.Heatmap.TrackedObjectsView
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                val navController = rememberNavController()
                val sharedReportGeneratorVM: ReportGeneratorVM = viewModel(factory = ReportGeneratorVMFactory(navController))

                Scaffold(

                        topBar = {
                            HeaderComponent(
                                title = GetNavBarTitle(navController, sharedReportGeneratorVM),
                                onBackPressed = {
                                    navController.popBackStack()
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
                        composable("report") {
                            ReportPage(navController = navController, reportGeneratorVM = sharedReportGeneratorVM)
                        }


                        composable("tracked_objects"){
                           TrackedObjectsView(navController = navController, reportGeneratorVM = sharedReportGeneratorVM)
                        }
                        composable("main_heatmap_report_view") {
                            MainHeatmapReportView(navController = navController, reportGeneratorVM = sharedReportGeneratorVM)
                        }
                        composable("tracked_object_details") {
                            TrackedObjectDetailsView(navController=navController, reportGeneratorVM = sharedReportGeneratorVM)
                        }
                        composable("heatmap") {
                            HeatmapPage()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun GetNavBarTitle(
        navController: NavHostController,
        sharedReportGeneratorVM: ReportGeneratorVM
    ): String {
        val currentRoute by navController.currentBackStackEntryAsState()
        var title = ""
        currentRoute?.destination?.route?.let { route ->
            title = when (route) {
                "login" -> "Login"
                "register" -> "register"
                "home" -> "Home"
                "report" -> "Report"
                "heatmap" -> "Heatmap"
                "tracked_objects" -> "Tracked Objects" + "\n${ convertDateFormat(sharedReportGeneratorVM.selectedStartDate)} - ${convertDateFormat(sharedReportGeneratorVM.selectedEndDate)}"
                "main_heatmap_report_view" -> "Main Heatmap Report " + "\n${ convertDateFormat(sharedReportGeneratorVM.selectedStartDate)} - ${convertDateFormat(sharedReportGeneratorVM.selectedEndDate)}"
                "tracked_object_details" -> "Tracked Object Details" + "\n${ convertDateFormat(sharedReportGeneratorVM.selectedStartDate)} - ${convertDateFormat(sharedReportGeneratorVM.selectedEndDate)}"
                else -> "Unknown Page"
            }
        }
        return title
    }
}

private fun convertDateFormat(date: String): String {
    val originalFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val dateObj = LocalDate.parse(date, originalFormatter)

    val newFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    return dateObj.format(newFormatter)
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface {
            content()
        }
    }
}


@Composable
fun isLoginOrRegister(navController: NavController): Boolean {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    return currentRoute == "login" || currentRoute == "register"
}