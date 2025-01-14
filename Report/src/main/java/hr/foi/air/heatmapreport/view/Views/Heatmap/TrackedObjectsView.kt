package hr.foi.air.heatmapreport.view.Views.Heatmap


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVM
import hr.foi.air.heatmapreport.view.ViewModels.ReportGeneratorVMFactory
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistoryGET

var _navController:NavController?=null
var _reportGeneratorVM:ReportGeneratorVM?=null
@Composable
fun TrackedObjectsView(navController: NavController,reportGeneratorVM: ReportGeneratorVM) {
    _navController=navController
    _reportGeneratorVM=reportGeneratorVM
    val modifiedResult by reportGeneratorVM.modifiedResult.observeAsState(initial = emptyList())



    modifiedResult.let {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(
            text = reportGeneratorVM.selectedStartDate+"-"+reportGeneratorVM.selectedEndDate+" "+reportGeneratorVM.selectedStartTime+":"+reportGeneratorVM.selectedEndTime,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "Tracked Objects",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))



        LazyColumn {
            items(it.groupBy {group-> group.assetName }.values.toList()!!) { item ->
                TrackedObjectItem(it.filter { filter-> filter.assetName==it.first().assetName })
            }
        }
    }
    }

}

@Composable
fun TrackedObjectItem(list: List<AssetPositionHistoryGET>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { OpenItemDetails(list) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Object Icon",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = list.first().assetName, modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Details")
    }
}

fun OpenItemDetails(list: List<AssetPositionHistoryGET>) {
    _reportGeneratorVM!!.selectedObjectDetail=list
 _navController!!.navigate("tracked_object_details")
}

