package hr.foi.air.heatmapreport.view.Views

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.view.Components.DateRangePickerDialog
import hr.foi.air.heatmapreport.view.Components.CustomTimePicker
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM
import hr.foi.air.heatmapreport.view.Views.components.AssetZoneHistoryTable
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetZoneHistory
import hr.foi.air.heatmapreport.view.interfaces.IReport
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ZoneReport(
    override var _navController: NavController,
    override var sharedReportGeneratorVM: ReportGeneratorVM
) : IReport {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun GetReport() {
        var assetZoneHistoryList by remember { mutableStateOf<List<AssetZoneHistory>?>(null) }
        val coroutineScope = rememberCoroutineScope()

        // Date and Time filters
        var startDate by remember { mutableStateOf<String?>(null) }
        var endDate by remember { mutableStateOf<String?>(null) }
        var startTime by remember { mutableStateOf<String?>(null) }
        var endTime by remember { mutableStateOf<String?>(null) }

        var showDateRangePicker by remember { mutableStateOf(false) }

        // Observe LiveData from ReportGeneratorVM
        val liveData = sharedReportGeneratorVM.assetZoneHistoryList.observeAsState()

        // Update the UI when the LiveData changes
        assetZoneHistoryList = liveData.value

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Zone Report", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Date Range Filter
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { showDateRangePicker = true }) {
                    Text(text = "Date Range: ${startDate ?: "Select"} - ${endDate ?: "Select"}")
                }
            }

            if (showDateRangePicker) {
                DateRangePickerDialog(
                    isVisible = remember { mutableStateOf(true) },
                    onDateRangeSelected = { selectedStartDate, selectedEndDate ->
                        // Assuming selectedStartDate and selectedEndDate are strings in dd/MM/yyyy format
                        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

                        startDate = outputFormat.format(inputFormat.parse(selectedStartDate)!!)
                        endDate = outputFormat.format(inputFormat.parse(selectedEndDate)!!)
                        showDateRangePicker = false

                        // Log when date range is selected
                        Log.d("ZoneReport", "Date Range Selected: Start Date = $startDate, End Date = $endDate")
                    }
                )
            }


            // Time Filter - Only Start Time Picker, End Time will default to current time
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomTimePicker(title = "Start Time", onTimeSelected = { time ->
                    startTime = "$time:00"

                    // Log when start time is selected
                    Log.d("ZoneReport", "Start Time Selected: $startTime")
                })
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomTimePicker(title = "End Time", onTimeSelected = { time ->
                    endTime = "$time:00"

                    // Log when end time is selected
                    Log.d("ZoneReport", "End Time Selected: $endTime")
                })
            }


            // Filter Button (you could use this to refresh data based on selected filters)
            Button(
                onClick = {
                    // Ensure all filters are provided
                    if (!startDate.isNullOrEmpty() && !endDate.isNullOrEmpty() && !startTime.isNullOrEmpty() && !endTime.isNullOrEmpty()) {
                        // Log the filters before applying them
                        Log.d("ZoneReport", "Applying Filters: Start Date = $startDate, End Date = $endDate, Start Time = $startTime, End Time = $endTime")

                        // Launch a coroutine to call the suspend function
                        coroutineScope.launch {
                            sharedReportGeneratorVM.getAssetZoneHistoryByDateAndTimeRange(
                                startDate!!,
                                endDate!!,
                                startTime!!,
                                endTime!! // Use the updated end time
                            )
                        }
                    } else {
                        Log.e("ZoneReport", "Please select all filters before applying.")
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Apply Filters")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display AssetZoneHistoryTable with applied filters
            if (assetZoneHistoryList != null) {
                AssetZoneHistoryTable(sharedReportGeneratorVM)
            } else {
                Text("Loading...")
            }
        }
    }
}
