package hr.foi.air.heatmapreport.view.Views.Heatmap

import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.util.Log.*
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.R
import hr.foi.air.heatmapreport.view.Components.HeatmapView
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM
import android.util.Base64
import hr.foi.air.heatmapreport.view.data.models.Entities.FloorMap


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainHeatmapReportView(navController: NavController, reportGeneratorVM: ReportGeneratorVM) {
    val result by reportGeneratorVM.result.observeAsState(initial = emptyList())
    val modifiedResult by reportGeneratorVM.modifiedResult.observeAsState(initial = emptyList())
    val floormapList by reportGeneratorVM.floormapList.observeAsState(initial = emptyList())
    var expanded by remember { mutableStateOf(false) }

    var painter by remember { mutableStateOf<BitmapPainter?>(reportGeneratorVM.Convert64BaseToBitmapPainter(floormapList?.first()!!.image)) }

    d("MainHeatmapReportView", "Result: $result")
    if (result!!.isEmpty() || floormapList!!.isEmpty()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "No data available", style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        if (reportGeneratorVM.selectedFloor == "") {
            reportGeneratorVM.updateModifiedResult(result!!.filter { it.floorMapId == result!!.first().floorMapId })
            reportGeneratorVM.selectedFloor = result!!.first().floorMapName
            painter=reportGeneratorVM.Convert64BaseToBitmapPainter(floormapList!!.first().image)

            Log.d("MainHeatmapReportViewFloormapList", floormapList?.size.toString())
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Selected floor", style = MaterialTheme.typography.bodyMedium)

            Box {
                OutlinedButton(onClick = { expanded = true }) {
                    Text(text = reportGeneratorVM.selectedFloor, style = MaterialTheme.typography.bodyMedium)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    reportGeneratorVM.floormapList.observeAsState().value?.forEach { floor ->

                        DropdownMenuItem(text = { Text(floor.name) }, onClick = {
                            expanded = false
                            reportGeneratorVM.selectedFloor = floor.name
                            reportGeneratorVM.updateModifiedResult(result!!.filter { it.floorMapName == floor.name })
                            painter=reportGeneratorVM.Convert64BaseToBitmapPainter(floormapList!!.first{it.name==floor.name}.image)
                            d("MainHeatmapReportViewPainter", painter.toString())

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
                    .size(1000.dp)
                    .border(1.dp, Color.Gray)
            ) {
                Log.d("MainHeatmapReportViewPainter", "Painter: $painter")
                if (painter != null) {
                    Image(
                        painter = painter!!,
                        contentDescription = "Base64 Image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(500.dp)
                    )
                    HeatmapView(assetPositions = modifiedResult)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))



        /*  Button(
              onClick = { },
              modifier = Modifier.fillMaxWidth().height(50.dp)
          ) {
              Text("Export to PDF")
          }*/
    }
}







