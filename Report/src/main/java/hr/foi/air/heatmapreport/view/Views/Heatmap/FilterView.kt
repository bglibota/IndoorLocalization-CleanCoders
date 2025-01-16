@file:OptIn(ExperimentalMaterial3Api::class)

package hr.foi.air.heatmapreport.view.Views.Heatmap


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.view.Components.DateRangePickerDialog
import hr.foi.air.heatmapreport.view.Components.DialWithDialogExample
import hr.foi.air.heatmapreport.view.Components.ShowDialog
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM
import hr.foi.air.heatmapreport.view.interfaces.IReport


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
fun HeatmapReportView(navController: NavController, reportGeneratorVM: ReportGeneratorVM) {
    val selectedStartTime = remember { mutableStateOf("00:00") }
    val selectedEndTime = remember { mutableStateOf("23:00") }
    val selectedStartDate = remember { mutableStateOf<String?>(null) }
    val selectedEndDate = remember { mutableStateOf<String?>(null) }
    val showDatePicker = remember { mutableStateOf(false) }
    val showStartTimePicker = remember { mutableStateOf(false) }
    val showEndTimePicker = remember { mutableStateOf(false) }
    var showDialog = remember { mutableStateOf(false) }
    DateRangePickerDialog(
        isVisible = showDatePicker,
        onDateRangeSelected = { startDate, endDate ->
            selectedStartDate.value = startDate
            selectedEndDate.value = endDate
            if(endDate!=null && startDate!=null){
                showDatePicker.value=false
            }
        }
    )
    if (showStartTimePicker.value) {
        DialWithDialogExample(
            onConfirm = { timePickerState ->
                val hour = timePickerState.hour
                val minute = timePickerState.minute
                selectedStartTime.value = String.format("%02d:%02d", hour, minute)
                showStartTimePicker.value = false
            },
            onDismiss = { showStartTimePicker.value = false }
        )
    }

    if (showEndTimePicker.value) {
        DialWithDialogExample(
            onConfirm = { timePickerState ->
                val hour = timePickerState.hour
                val minute = timePickerState.minute
                selectedEndTime.value = String.format("%02d:%02d", hour, minute)
                showEndTimePicker.value = false
            },
            onDismiss = { showEndTimePicker.value = false }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Selected range:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "Start: ${selectedStartDate.value ?: "Not selected"}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "End: ${selectedEndDate.value ?: "Not selected"}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Button(
                    onClick = { showDatePicker.value = true }
                ) {
                    Text(text = "Pick Date Range")
                }
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = selectedStartTime.value,
                onValueChange = { selectedStartTime.value = it },
                label = { Text("Start time") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { showStartTimePicker.value = true }) {
                        Icon(imageVector = Icons.Sharp.Create, contentDescription = "Time Picker")
                    }
                }
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = selectedEndTime.value,
                onValueChange = { selectedEndTime.value = it },
                label = { Text("End time") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { showEndTimePicker.value = true }) {
                        Icon(imageVector = Icons.Sharp.Create, contentDescription = "Time Picker")
                    }
                }
            )
        }

        Button(onClick = {
            if(selectedStartDate.value==null || selectedEndDate.value==null){
                showDialog.value=true
                return@Button
            }
            reportGeneratorVM.loadHeatmapReport(
                selectedStartDate.value!!,
                selectedEndDate.value!!,
                selectedStartTime.value,
                selectedEndTime.value
            )
        }) {
            Text(text = "Generate")
        }
        if (showDialog.value) {
            ShowDialog(showDialog, "Date is not selected", "Please select date range")
        }
    }
}






