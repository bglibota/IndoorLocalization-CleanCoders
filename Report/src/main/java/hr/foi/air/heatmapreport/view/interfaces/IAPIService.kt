package hr.foi.air.heatmapreport.view.interfaces


import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistoryGET
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetPositionHistoryPOST
import hr.foi.air.heatmapreport.view.data.models.Entities.FloorMap
import hr.foi.air.heatmapreport.view.data.models.Entities.AssetZoneHistory
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface IAPIService{
    @GET("/HeatmapReport/GetAssetPositionHistoryByDateRangeAndTimeRange/{startDate}/{endDate}/{startTime}/{endTime}")
    suspend fun GetAssetPositionHistoryByDateRangeAndTimeRange(@Path("startDate") startDate: String,
                                                               @Path("endDate") endDate: String,
                                                               @Path("startTime") startTime: String,
                                                               @Path("endTime") endTime: String) : Response<List<AssetPositionHistoryGET>>
    @POST("/HeatmapReport/AddAssetPositionHistory")
    suspend fun AddAssetPositionHistory(@Body assetPositionHistoryPOST: AssetPositionHistoryPOST): Response<Int>

    @GET("/api/FloorMap/GetAllFloorMapsWithoutAssetHistories")
    suspend fun GetAllFloorMaps(): Response<List<FloorMap>>

    // Get Asset Zone History by Date Range and Time Range
    @GET("/ZoneReport/GetAssetZoneHistoryByDateRangeAndTimeRange/{startDate}/{endDate}/{startTime}/{endTime}")
    suspend fun GetAssetZoneHistoryByDateAndTimeRange(
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String,
        @Path("startTime") startTime: String,
        @Path("endTime") endTime: String
    ) : Response<List<AssetZoneHistory>>

    // Get Asset Zone History by Asset Id and Date/Time Range
    @GET("/ZoneReport/GetAssetZoneHistoryByAssetIdAndDateRangeAndTimeRange/{assetId}/{startDate}/{endDate}/{startTime}/{endTime}")
    suspend fun GetAssetZoneHistoryByAssetIdAndDateAndTimeRange(
        @Path("assetId") assetId: Int,
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String,
        @Path("startTime") startTime: String,
        @Path("endTime") endTime: String
    ): Response<List<AssetZoneHistory>>

    // Get Asset Zone History by Floor Map Id and Date/Time Range
    @GET("/ZoneReport/GetAssetZoneHistoryByFloorMapIdAndDateRangeAndTimeRange/{floorMapId}/{startDate}/{endDate}/{startTime}/{endTime}")
    suspend fun GetAssetZoneHistoryByFloorMapIdAndDateAndTimeRange(
        @Path("floorMapId") floorMapId: Int,
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String,
        @Path("startTime") startTime: String,
        @Path("endTime") endTime: String
    ): Response<List<AssetZoneHistory>>
}