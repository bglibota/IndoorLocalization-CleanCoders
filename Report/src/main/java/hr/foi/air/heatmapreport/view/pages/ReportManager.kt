package hr.foi.air.heatmapreport.view.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.view.ReportViews.HeatmapReportView
import hr.foi.air.heatmapreport.view.data.models.ReportTypes
import hr.foi.air.report.interfaces.IReport

class Report(override val _navController: NavController) : IReport {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun GetReport(reportTypes: ReportTypes) {
        when (reportTypes)
        {
            ReportTypes.HEATMAP_REPORT -> HeatmapReportView(navController = _navController)
            else -> throw IllegalArgumentException("Ne postoji ta vrsta reporta")
        }
    }
}