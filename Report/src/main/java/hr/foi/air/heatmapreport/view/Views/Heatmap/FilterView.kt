package hr.foi.air.heatmapreport.view.Views.Heatmap


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.R
import hr.foi.air.heatmapreport.view.Components.CustomDatePicker
import hr.foi.air.heatmapreport.view.Components.CustomTimePicker
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM
import hr.foi.air.heatmapreport.view.data.helpers.DateTimeConverter
import hr.foi.air.heatmapreport.view.interfaces.IReport
import java.time.LocalDate

import java.util.Date


class HeatmapReport(override var _navController: NavController,
                    override var sharedReportGeneratorVM: ReportGeneratorVM
): IReport {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun GetReport() {
       HeatmapReportView(navController=_navController,sharedReportGeneratorVM)
    }

}
@RequiresApi(Build.VERSION_CODES.O)


@Composable
fun HeatmapReportView(navController: NavController, reportGeneratorVM: ReportGeneratorVM){
    var selectedStartTime = remember { mutableStateOf("12:00") }
    var selectedEndTime = remember { mutableStateOf("12:00") }
    val datePicker = remember { CustomDatePicker(navController.context) }
    val selectedDate = remember { mutableStateOf(DateTimeConverter().ConvertDateToFormat(LocalDate.now().toString(),"dd.MM.yyyy.")) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = MaterialTheme.shapes.small,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Selected date:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    Text(
                        text = selectedDate.value,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Calendar button
                Button(
                    onClick = {
                        datePicker.show { date ->
                            selectedDate.value = date
                        }
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "calendar",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        CustomTimePicker(
            title = "Time From",

            initialHour = (Date()).hours,
            initialMinute = (Date()).minutes,
            onTimeSelected = { time -> selectedStartTime.value = time }
        )
        CustomTimePicker(
            title = "Time Until",

            initialHour = (Date()).hours,
            initialMinute = (Date()).minutes,
            onTimeSelected = { time -> selectedEndTime.value = time }
        )
        Button(onClick = {
            /*reportMng.loadData(
                selectedDate.value,
                selectedStartTime.value,
                selectedEndTime.value)*/
            reportGeneratorVM.loadData(
                "13.12.2024.",
                "05:00",
                "20:00")


                navController.navigate("main_heatmap_report_view")

        }) {
            Text(text = "Generate")
        }

    }


}






