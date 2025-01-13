package hr.foi.air.heatmapreport.view.interfaces


import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface IAPIService{
    @GET("/HeatmapReport/GetAssetPositionHistoryByDateRangeAndTimeRange/{startDate}/{endDate}/{startTime}/{endTime}")
    suspend fun GetAssetPositionHistoryByDateRangeAndTimeRange(@Path("startDate") startDate: String,
                                                               @Path("endDate") endDate: String,
                                                               @Path("startTime") startTime: String,
                                                               @Path("endTime") endTime: String) : Response<List<AssetPositionHistory>>


}