package hr.foi.air.heatmapreport.view.Views.Heatmap

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.R
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM


@Composable
fun MainHeatmapReportView(navController: NavController, reportGeneratorVM: ReportGeneratorVM) {
    val result by reportGeneratorVM.result.observeAsState(initial = emptyList())
    val modifiedResult by reportGeneratorVM.modifiedResult.observeAsState(initial = emptyList())
    var expanded by remember { mutableStateOf(false) }

    if (result!!.isEmpty()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        if (reportGeneratorVM.selectedFloor == "") {
            reportGeneratorVM.updateModifiedResult(result!!.filter { it.floorMapId == result!!.first().floorMapId })
            reportGeneratorVM.selectedFloor = result!!.first().floorMapName
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Summary", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Selected floor", style = MaterialTheme.typography.bodyMedium)

            Box {
                OutlinedButton(onClick = { expanded = true }) {
                    Text(text = reportGeneratorVM.selectedFloor, style = MaterialTheme.typography.bodyMedium)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    result!!.map { it.floorMapName }.distinct().forEach { floor ->
                        DropdownMenuItem(text = { Text(floor) }, onClick = {
                            expanded = false
                            reportGeneratorVM.selectedFloor = floor
                            reportGeneratorVM.updateModifiedResult(result!!.filter { it.floorMapName == floor })
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Number of tracked objects: ${modifiedResult.map { it.assetName }.distinct().size}",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(onClick = { navController.navigate("tracked_objects") }) {
                Text("View Objects")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Heatmap", style = MaterialTheme.typography.bodySmall)
            Box(
                modifier = Modifier
                    .size(500.dp)
                    .border(1.dp, Color.Gray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tlocrt),
                    contentDescription = "Floor Map",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()

                )
                HeatmapView(assetPositions = modifiedResult)
            }

            Spacer(modifier = Modifier.height(16.dp))



            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Export to PDF")
            }
        }
    }
}



