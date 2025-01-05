package hr.foi.air.heatmapreport.view.ViewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.foi.air.heatmapreport.view.data.api.RestAPI_GET
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistory
import kotlinx.coroutines.launch


class ReportGeneratorVM(var _context: Context):ViewModel(){
    private var result:List<AssetPositionHistory>?=null

    fun loadData(date: String, startTime: String, endTime: String) {
        viewModelScope.launch {

           result= RestAPI_GET(_context).getAllPositionHistoryByDateAndTimeRange(date,startTime,endTime )
        }
    }
    fun getPositionHistoryData(): List<AssetPositionHistory>? {
        return result

    }
}