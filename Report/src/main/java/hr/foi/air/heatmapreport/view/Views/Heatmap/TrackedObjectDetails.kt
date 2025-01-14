package hr.foi.air.heatmapreport.view.Views.Heatmap

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM
import hr.foi.air.heatmapreport.view.data.helpers.DateTimeConverter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackedObjectDetailsView(navController: NavController, reportGeneratorVM: ReportGeneratorVM){
    val selectedObjectList = reportGeneratorVM.selectedObjectDetail

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Object Details",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Object name: ${selectedObjectList!!.first().assetName}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        LazyColumn {
            items(selectedObjectList) { item ->
                var localDate=DateTimeConverter().convertDateTimeToCustomFormat(dateTimeString = item.dateTime,format = "dd.MM.yyyy.")
                var localTime=DateTimeConverter().convertDateTimeToCustomFormat(dateTimeString = item.dateTime,format = "HH:mm")
                Text(
                    text = "Date: ${localDate} Time:${localTime}"+"\n"+"X: "+String.format("%.2f", item.x)+" Y: "+String.format("%.2f", item.y),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

            }
        }

    }
}