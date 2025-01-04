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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.R
import hr.foi.air.heatmapreport.view.Components.CustomDatePicker
import hr.foi.air.heatmapreport.view.Components.CustomTimePicker
import hr.foi.air.heatmapreport.view.data.models.ReportTypes
import hr.foi.air.heatmapreport.view.pages.Report
import hr.foi.air.report.interfaces.IReport
import java.time.LocalDate
import java.util.Date

@Composable
fun ReportPage(navController: NavController) {
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
        // RadioButton za svaki ReportTypes
        Text("Select Report Type:")
        ReportTypes.entries.forEach { reportType ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (reportType == selectedReportType),
                    onClick = { selectedReportType = reportType }
                )
                Text(reportType.type)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {
            showReport = true
        }) {
            Text(text = "Generate Report")
        }

        // Prikaz izveštaja nakon klika


    }
}
    if (showReport) {
        Spacer(modifier = Modifier.height(16.dp))
        ShowReport(selectedReportType, navController)
    }
}

@Composable
fun ShowReport(reportTypes: ReportTypes, navController: NavController) {
    val report: IReport = when (reportTypes) {
        ReportTypes.HEATMAP_REPORT -> Report(navController)
        ReportTypes.ZONE_REPORT -> Report(navController)
        else -> throw IllegalArgumentException("Nepoznat tip izveštaja: $reportTypes")
    }

    report.GetReport(reportTypes)
}
