package hr.foi.air.heatmapreport.view.interfaces

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

interface IReport {
    abstract var _navController:NavController
    @Composable
    fun GetReport()
}
