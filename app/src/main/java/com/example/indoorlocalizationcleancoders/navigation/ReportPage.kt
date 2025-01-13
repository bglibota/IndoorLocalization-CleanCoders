package com.example.indoorlocalizationcleancoders.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button

import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM
import hr.foi.air.heatmapreport.view.Views.Heatmap.HeatmapReport
import hr.foi.air.heatmapreport.view.Views.Heatmap.TrackedObjectItem
import hr.foi.air.heatmapreport.view.Views.ZoneReport
import hr.foi.air.heatmapreport.view.data.models.ReportTypes
import hr.foi.air.heatmapreport.view.interfaces.IReport

@Composable
fun ReportPage(navController: NavController, reportGeneratorVM: ReportGeneratorVM) {
    var selectedReportType by remember { mutableStateOf(ReportTypes.HEATMAP_REPORT) }
    var showReport by remember { mutableStateOf(false) }
if(!showReport) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Select Report Type:")
        ReportTypes.entries.forEach { reportType ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (reportType == selectedReportType),
                    onClick = { selectedReportType = reportType }
                )
                Text(
                    text = reportType.type,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            showReport = true
        }) {
            Text(text = "Select Report")
        }




    }
}
    if (showReport) {
        Spacer(modifier = Modifier.height(16.dp))
        val reportInstance = getReportInstance(selectedReportType, navController,reportGeneratorVM)
        ShowReport(reportInstance)
    }
}

@Composable
fun ShowReport(report: IReport) {
    report.GetReport()
}
@Composable
fun getReportInstance(reportType: ReportTypes, navController: NavController,reportGeneratorVM: ReportGeneratorVM): IReport {
    return when (reportType) {
        ReportTypes.HEATMAP_REPORT -> HeatmapReport(navController, reportGeneratorVM )
        ReportTypes.ZONE_REPORT -> ZoneReport(navController,reportGeneratorVM)
    }
}