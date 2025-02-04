package hr.foi.air.heatmapreport.view.ViewModels

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import hr.foi.air.heatmapreport.view.data.api.RestAPI_GET
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistoryGET
import hr.foi.air.heatmapreport.view.data.models.Entities.FloorMap
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetZoneHistory
import kotlinx.coroutines.launch

class ReportGeneratorVM(var navController: NavController): ViewModel() {

    var selectedStartDate: String = ""
    var selectedEndDate: String = ""
    var selectedStartTime: String = ""
    var selectedEndTime: String = ""
    var selectedFloor: String = ""

    var selectedObjectDetail: List<AssetPositionHistoryGET>? = null

    private var _fetchedFloormaps = MutableLiveData<List<FloorMap>?>()
    val floormapList: LiveData<List<FloorMap>?> get() = _fetchedFloormaps

    private var _result = MutableLiveData<List<AssetPositionHistoryGET>?>()
    val result: LiveData<List<AssetPositionHistoryGET>?> get() = _result

    private var _modifiedResult = MutableLiveData<List<AssetPositionHistoryGET>>()
    val modifiedResult: LiveData<List<AssetPositionHistoryGET>> get() = _modifiedResult

    private var _assetZoneHistoryList = MutableLiveData<List<AssetZoneHistory>?>()
    val assetZoneHistoryList: LiveData<List<AssetZoneHistory>?> get() = _assetZoneHistoryList

    fun updateModifiedResult(newResult: List<AssetPositionHistoryGET>) {
        _modifiedResult.value = newResult
    }

    fun loadFloorMaps() {
        viewModelScope.launch {
            try {
                val fetchedResult = RestAPI_GET().getAllFloorMaps()
                _fetchedFloormaps.value = fetchedResult
            } catch (e: Exception) {
                Log.e("ReportGeneratorVM", "Error fetching data: ${e.message}")
            }
        }
    }

    fun loadHeatmapReport(startDate: String, endDate: String, startTime: String, endTime: String) {
        CleanStart()

        viewModelScope.launch {
            loadFloorMaps()
            selectedStartDate = startDate
            selectedEndDate = endDate
            selectedStartTime = startTime
            selectedEndTime = endTime
            try {
                val fetchedResult = RestAPI_GET().getAllPositionHistoryByDateAndTimeRange(
                    startDate, endDate, startTime, endTime
                )
                _result.value = fetchedResult
                navController.navigate("main_heatmap_report_view")
            } catch (e: Exception) {
                Log.e("ReportGeneratorVM", "Error fetching data: ${e.message}")
            }
        }
    }

    fun CleanStart() {
        selectedStartDate = ""
        selectedEndDate = ""
        selectedStartTime = ""
        selectedEndTime = ""
        selectedFloor = ""
        Log.d("ReportGeneratorVM", "CleanStart called")
    }

    fun Convert64BaseToBitmapPainter(base64: String): BitmapPainter? {
        val decodedString = Base64.decode(base64, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        return BitmapPainter(bitmap.asImageBitmap())
    }




    // Fetch AssetZoneHistory by Date and Time Range
    suspend fun getAssetZoneHistoryByDateAndTimeRange(
        startDate: String,
        endDate: String,
        startTime: String,
        endTime: String
    ): List<AssetZoneHistory>? {
        return try {
            val fetchedResult = RestAPI_GET().getAssetZoneHistoryByDateAndTimeRange(
                startDate, endDate, startTime, endTime
            )
            _assetZoneHistoryList.postValue(fetchedResult)  // Post the result to LiveData
            fetchedResult
        } catch (e: Exception) {
            Log.e("ReportGeneratorVM", "Error fetching AssetZoneHistory: ${e.message}")
            null
        }
    }

    // Fetch AssetZoneHistory by AssetId and Date/Time Range
    suspend fun getAssetZoneHistoryByAssetIdAndDateAndTimeRange(
        assetId: Int,
        startDate: String,
        endDate: String,
        startTime: String,
        endTime: String
    ): List<AssetZoneHistory>? {
        return try {
            val fetchedResult = RestAPI_GET().getAssetZoneHistoryByAssetIdAndDateTimeRange(
                assetId, startDate, endDate, startTime, endTime
            )
            _assetZoneHistoryList.postValue(fetchedResult)  // Post the result to LiveData
            fetchedResult
        } catch (e: Exception) {
            Log.e("ReportGeneratorVM", "Error fetching AssetZoneHistory: ${e.message}")
            null
        }
    }

    // Fetch AssetZoneHistory by FloorMapId and Date/Time Range
    suspend fun getAssetZoneHistoryByFloorMapIdAndDateAndTimeRange(
        floormapId: Int,
        startDate: String,
        endDate: String,
        startTime: String,
        endTime: String
    ): List<AssetZoneHistory>? {
        return try {
            val fetchedResult = RestAPI_GET().getAssetZoneHistoryByFloorMapIdAndDateTimeRange(
                floormapId, startDate, endDate, startTime, endTime
            )
            _assetZoneHistoryList.postValue(fetchedResult)  // Post the result to LiveData
            fetchedResult
        } catch (e: Exception) {
            Log.e("ReportGeneratorVM", "Error fetching AssetZoneHistory: ${e.message}")
            null
        }
    }
}

class ReportGeneratorVMFactory(var _navController: NavController) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReportGeneratorVM(navController = _navController) as T
    }
}
