package hr.foi.air.heatmapreport.view.Views

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.view.interfaces.IReport

class ZoneReport(override var _navController: NavController): IReport {
    @Composable
    override fun GetReport() {
        TODO("Not yet implemented")
    }
}

@Composable
fun ZoneReportView(report: IReport) {
    report.GetReport()
}