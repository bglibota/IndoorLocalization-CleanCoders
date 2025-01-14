package hr.foi.air.heatmapreport.view.ViewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hr.foi.air.heatmapreport.view.data.api.RestAPI_GET
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistoryGET
import kotlinx.coroutines.launch

class ReportGeneratorVM(var _context: Context): ViewModel() {

    var selectedStartDate:String=""
    var selectedEndDate:String=""
    var selectedStartTime:String=""
    var selectedEndTime:String=""
    var selectedFloor:String=""


    var selectedObjectDetail: List<AssetPositionHistoryGET>? = null

    private val _result = MutableLiveData<List<AssetPositionHistoryGET>?>()
    val result: LiveData<List<AssetPositionHistoryGET>?> get() = _result

    private val _modifiedResult = MutableLiveData<List<AssetPositionHistoryGET>>()
    val modifiedResult: LiveData<List<AssetPositionHistoryGET>> get() = _modifiedResult



    fun updateModifiedResult(newResult: List<AssetPositionHistoryGET>) {
        _modifiedResult.value = newResult
    }


    fun loadDataHeatmapReport(startDate: String,endDate:String, startTime: String, endTime: String) {
        selectedStartDate=startDate
        selectedEndDate=endDate
        selectedStartTime=startTime
        selectedEndTime=endTime


        viewModelScope.launch {

            try {
                val fetchedResult = RestAPI_GET(_context).getAllPositionHistoryByDateAndTimeRange(startDate,endDate, startTime, endTime)
                _result.value=fetchedResult

            } catch (e: Exception) {
                Log.e("ReportGeneratorVM", "Error fetching data: ${e.message}")
            }

        }
    }

}

class ReportGeneratorVMFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReportGeneratorVM(context) as T
    }
}
