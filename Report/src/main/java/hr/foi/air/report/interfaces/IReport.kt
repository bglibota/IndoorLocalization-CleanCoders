package hr.foi.air.report.interfaces

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.view.data.models.ReportTypes
import java.util.Objects

interface IReport {
    val _navController: NavController

    @Composable
    fun GetReport(reportTypes: ReportTypes)
}
