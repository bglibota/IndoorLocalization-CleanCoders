package hr.foi.air.heatmapreport.view.ViewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hr.foi.air.heatmapreport.view.data.api.RestAPI_GET
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistory
import kotlinx.coroutines.launch

class ReportGeneratorVM(var _context: Context): ViewModel() {
    private val _result = MutableLiveData<List<AssetPositionHistory>?>()
    val result: LiveData<List<AssetPositionHistory>?> get() = _result


    fun loadData(date: String, startTime: String, endTime: String) {
        viewModelScope.launch {

            try {
                val fetchedResult = RestAPI_GET(_context).getAllPositionHistoryByDateAndTimeRange(date, startTime, endTime)
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
