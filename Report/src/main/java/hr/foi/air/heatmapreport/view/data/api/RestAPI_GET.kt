package hr.foi.air.heatmapreport.view.data.api

import android.content.Context
import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale

import androidx.annotation.RequiresApi
import hr.foi.air.heatmapreport.view.data.helpers.DateTimeConverter
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistoryGET
import hr.foi.air.heatmapreport.view.data.models.Entities.FloorMap
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetZoneHistory

class RestAPI_GET() {

    @RequiresApi(Build.VERSION_CODES.O)
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

    suspend fun getAllFloorMaps(): List<FloorMap>? {

        return try {
            val apiService = ApiClient.getApiService().GetAllFloorMaps()
            if (apiService.isSuccessful) {
                apiService.body()
            }
            else {
                Log.e("ReportGeneratorVM", "Error response: ${apiService.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("ReportGeneratorVM", "Error fetching data", e)
            null

        }
    }
    suspend fun getAssetZoneHistoryByDateAndTimeRange(
        startDate: String, endDate: String, startTime: String, endTime: String
    ): List<AssetZoneHistory>? {
        return try {
            // Ensure dates are in yyyy-MM-dd format
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedStartDate = formatter.format(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).parse(startDate))
            val parsedEndDate = formatter.format(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).parse(endDate))

            Log.d("DebugDate", "Parsed Start Date: $parsedStartDate, Parsed End Date: $parsedEndDate")

            // Call the API
            val apiService = ApiClient.getApiService().GetAssetZoneHistoryByDateAndTimeRange(
                parsedStartDate, parsedEndDate, startTime, endTime
            )

            if (apiService.isSuccessful) {
                Log.d("RestAPI_GET", "Data loaded: ${apiService.body()}")
                apiService.body()
            } else {
                Log.e("RestAPI_GET", "Error response: ${apiService.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("RestAPI_GET", "Error fetching AssetZoneHistory by Date and Time Range", e)
            null
        }
    }




    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAssetZoneHistoryByAssetIdAndDateTimeRange(
        assetId: Int, startDate: String, endDate: String, startTime: String, endTime: String
    ): List<AssetZoneHistory>? {
        return try {
            val newStartDateFormat = DateTimeConverter().ConvertDateToFormat(startDate, "yyyy-MM-dd")
            val newEndDateFormat = DateTimeConverter().ConvertDateToFormat(endDate, "yyyy-MM-dd")
            val apiService = ApiClient.getApiService()
                .GetAssetZoneHistoryByAssetIdAndDateAndTimeRange(assetId, newStartDateFormat, newEndDateFormat, startTime, endTime)

            if (apiService.isSuccessful) {
                Log.d("RestAPI_GET", "Data loaded: ${apiService.body()}")
                apiService.body() // Return the fetched data
            } else {
                Log.e("RestAPI_GET", "Error response: ${apiService.errorBody()?.string()}")
                null // Return null if the API call was unsuccessful
            }
        } catch (e: Exception) {
            Log.e("RestAPI_GET", "Error fetching AssetZoneHistory by Asset ID and Date/Time Range", e)
            null // Return null in case of an exception
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAssetZoneHistoryByFloorMapIdAndDateTimeRange(
        floormapId: Int, startDate: String, endDate: String, startTime: String, endTime: String
    ): List<AssetZoneHistory>? {
        return try {
            val newStartDateFormat = DateTimeConverter().ConvertDateToFormat(startDate, "yyyy-MM-dd")
            val newEndDateFormat = DateTimeConverter().ConvertDateToFormat(endDate, "yyyy-MM-dd")
            val apiService = ApiClient.getApiService()
                .GetAssetZoneHistoryByFloorMapIdAndDateAndTimeRange(floormapId, newStartDateFormat, newEndDateFormat, startTime, endTime)

            if (apiService.isSuccessful) {
                Log.d("RestAPI_GET", "Data loaded: ${apiService.body()}")
                apiService.body() // Return the fetched data
            } else {
                Log.e("RestAPI_GET", "Error response: ${apiService.errorBody()?.string()}")
                null // Return null if the API call was unsuccessful
            }
        } catch (e: Exception) {
            Log.e("RestAPI_GET", "Error fetching AssetZoneHistory by Floor Map and Date/Time Range", e)
            null // Return null in case of an exception
        }
    }


}