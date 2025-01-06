package hr.foi.air.heatmapreport.view.Views.Heatmap

import android.content.Context
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVMFactory


@Composable
fun MainHeatmapReportView(navController: NavController,reportGeneratorVM: ReportGeneratorVM) {
    val result by reportGeneratorVM.result.observeAsState()
    var selectedFloor = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        result.let {
            Log.d("ReportGeneratorVMinside", "Result: $it")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {


                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Summary", style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(16.dp))


                Text(text = "Selected floor", style = MaterialTheme.typography.bodyMedium)
                var expanded = remember { mutableStateOf(false) }

                Box {
                    OutlinedButton(onClick = { expanded.value = true }) {
                        Text(
                            text = selectedFloor.value,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }) {
                        it!!.filter { !it.floorMapName.isNullOrEmpty() }
                            .groupBy { it.floorMapName }.forEach { it ->
                            DropdownMenuItem(text = { Text(it.value.first().floorMapName) }, onClick = {
                                expanded.value = false
                                selectedFloor.value = it.value.first().floorMapName
                            })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "Number of tracked objects: " + result?.size.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Button(onClick = { navController.navigate("tracked_objects") }) {
                    Text("View Objects")
                }

                Spacer(modifier = Modifier.height(16.dp))


                Text(text = "Heatmap", style = MaterialTheme.typography.bodySmall)
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .border(1.dp, Color.Gray)
                ) {

                    Text(text = "Heatmap Image", modifier = Modifier.align(Alignment.Center))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Heatmap Analysis Section
                Text(text = "Heatmap analysis", style = MaterialTheme.typography.bodySmall)

                Text("Longest time spent in zone:", fontWeight = FontWeight.Bold)
                Text("Object name: Box 2")
                Text("Zone name: Zone 3")
                Text("Time spent in zone: 30 min")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Shortest time spent in zone:", fontWeight = FontWeight.Bold)
                Text("Object name: Box 3")
                Text("Zone name: Zone 1")
                Text("Time spent in zone: 15 sec")

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(15.dp)
                ) {
                    Text("Export to PDF")
                }
            }
        }
    }
}

