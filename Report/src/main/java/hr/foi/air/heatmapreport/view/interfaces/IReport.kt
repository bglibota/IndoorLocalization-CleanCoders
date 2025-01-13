package hr.foi.air.heatmapreport.view.interfaces

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM

interface IReport {
    abstract var _navController:NavController
    abstract var sharedReportGeneratorVM:ReportGeneratorVM
    @Composable
    fun GetReport()
}
