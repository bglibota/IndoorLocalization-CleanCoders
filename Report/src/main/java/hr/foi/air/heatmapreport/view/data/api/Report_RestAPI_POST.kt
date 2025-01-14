package hr.foi.air.heatmapreport.view.data.api

import android.util.Log
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistoryGET
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistoryPOST

class Report_RestAPI_POST() {
   suspend fun AddAssetPositionHistory(assetPositionHistoryPOST: AssetPositionHistoryPOST){

        val apiService = ApiClient.getApiService().AddAssetPositionHistory(assetPositionHistoryPOST)
        if(apiService.isSuccessful){
            Log.d("ReportGeneratorVMe", "Data loadede: ${apiService.body()}")
        }
        else{
            Log.e("ReportGeneratorVM", "Error response: ${apiService.errorBody()?.string()}")
        }

    }
}