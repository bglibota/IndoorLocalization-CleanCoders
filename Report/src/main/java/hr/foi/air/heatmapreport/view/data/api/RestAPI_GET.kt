package hr.foi.air.heatmapreport.view.data.api

import android.content.Context
import android.util.Log
import hr.foi.air.heatmapreport.view.data.helpers.DateTimeConverter
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistoryGET

class RestAPI_GET() {

    suspend fun getAllPositionHistoryByDateAndTimeRange(
        startDate: String, endDate:String, startTime: String, endTime: String
    ): List<AssetPositionHistoryGET>? {
        return try {
            var newStartDateFormat=DateTimeConverter().ConvertDateToFormat(startDate,"yyyy-MM-dd")
            var newEndDateFormat=DateTimeConverter().ConvertDateToFormat(endDate,"yyyy-MM-dd")
            val apiService = ApiClient.getApiService().GetAssetPositionHistoryByDateRangeAndTimeRange(newStartDateFormat,newEndDateFormat, startTime, endTime)
            if (apiService.isSuccessful) {
                Log.d("ReportGeneratorVMe", "Data loadede: ${apiService.body()}")

                apiService.body()
            } else {
                Log.e("ReportGeneratorVM", "Error response: ${apiService.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("ReportGeneratorVM", "Error fetching data", e)
            null
        }
    }
}