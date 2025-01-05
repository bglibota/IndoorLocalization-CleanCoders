package hr.foi.air.heatmapreport.view.data.api

import android.content.Context
import android.util.Log
import hr.foi.air.heatmapreport.view.data.helpers.DateTimeConverter
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistory

class RestAPI_GET(var context:Context) {

    suspend fun getAllPositionHistoryByDateAndTimeRange(
        date: String, startTime: String, endTime: String
    ): List<AssetPositionHistory>? {
        return try {
            var newDateFormat=DateTimeConverter().ConvertDateToFormat(date,"yyyy-MM-dd")
            val apiService = ApiClient.getApiService(context).getAllByDateAndTimeRange(newDateFormat, startTime, endTime)
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