package hr.foi.air.heatmapreport.view.Views

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM
import hr.foi.air.heatmapreport.view.interfaces.IReport

class ZoneReport(override var _navController: NavController,
                 override var sharedReportGeneratorVM: ReportGeneratorVM
): IReport {
    @Composable
    override fun GetReport() {
        TODO("Not yet implemented")
    }
}

@Composable
fun ZoneReportView(report: IReport) {
    report.GetReport()
}