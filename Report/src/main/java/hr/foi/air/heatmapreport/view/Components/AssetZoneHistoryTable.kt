package hr.foi.air.heatmapreport.view.Views.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetZoneHistory
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text

@RequiresApi(Build.VERSION_CODES.O)
fun parseToLocalDateTime(dateTime: String?): LocalDateTime? {
    return try {
        if (dateTime != null) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS") // Adjust for space and microseconds
            LocalDateTime.parse(dateTime, formatter)
        } else {
            null
        }
    } catch (e: Exception) {
        Log.e("DateTimeParsing", "Error parsing date: $dateTime", e)
        null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AssetZoneHistoryTable(sharedReportGeneratorVM: ReportGeneratorVM) {
    // Observe assetZoneHistoryList
    val assetZoneHistoryList = sharedReportGeneratorVM.assetZoneHistoryList.observeAsState()

    // Check if the list is empty or not
    val data = assetZoneHistoryList.value ?: emptyList()

    // Show content state with dynamic column widths
    ContentState(data = data)
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentState(data: List<AssetZoneHistory>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            // Table header row
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                TableHeader(title = "Asset ID", weight = 1f)
                TableHeader(title = "Zone ID", weight = 1f)
                TableHeader(title = "Enter Date & Time", weight = 2f)
                TableHeader(title = "Exit Date & Time", weight = 2f)
                TableHeader(title = "Retention Time", weight = 1.5f)
            }
        }

        // Displaying each item from the list
        items(data) { assetZoneHistory ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                TableCell(assetZoneHistory.assetId?.toString() ?: "N/A", weight = 1f)
                TableCell(assetZoneHistory.zoneId?.toString() ?: "N/A", weight = 1f)
                TableCell(parseToLocalDateTime(assetZoneHistory.enterDateTime)?.let { formatDateTime(it) } ?: "N/A", weight = 2f)
                TableCell(parseToLocalDateTime(assetZoneHistory.exitDateTime)?.let { formatDateTime(it) } ?: "N/A", weight = 2f)
                TableCell(parseRetentionTime(assetZoneHistory.retentionTime)?.let { formatRetentionTime(it) } ?: "N/A", weight = 1.5f)
            }
        }
    }
}

@Composable
fun TableHeader(title: String, weight: Float) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
        textAlign = TextAlign.Center,
        modifier = Modifier

            .padding(8.dp)
    )
}

@Composable
fun TableCell(content: String, weight: Float) {
    Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier

            .padding(8.dp)
    )
}






// Helper function to format LocalDateTime to a readable string
@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTime(dateTime: LocalDateTime?): String {
    return dateTime?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) ?: "N/A"
}

// Helper function to format Duration to a readable string
@RequiresApi(Build.VERSION_CODES.O)
fun formatRetentionTime(duration: Duration?): String {
    return duration?.let {
        val hours = it.toHours()
        val minutes = it.toMinutes() % 60
        val seconds = it.seconds % 60
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } ?: "N/A"
}

// Helper function to parse retention time string to Duration
@RequiresApi(Build.VERSION_CODES.O)
fun parseRetentionTime(retentionTime: String?): Duration? {
    return try {
        if (retentionTime != null) {
            val parts = retentionTime.split(":")
            val hours = parts[0].toLong()
            val minutes = parts[1].toLong()
            val seconds = parts[2].toDouble() // Handle fractional seconds
            val totalSeconds = hours * 3600 + minutes * 60 + seconds
            Duration.ofSeconds(totalSeconds.toLong(), (seconds % 1 * 1_000_000).toLong())
        } else {
            null
        }
    } catch (e: Exception) {
        Log.e("DurationParsing", "Error parsing retention time: $retentionTime", e)
        null
    }
}
