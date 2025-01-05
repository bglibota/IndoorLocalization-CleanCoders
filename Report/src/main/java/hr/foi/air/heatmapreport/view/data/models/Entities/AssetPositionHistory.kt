package hr.foi.air.heatmapreport.view.data.models.Entities

data class AssetPositionHistory(
    val id: Int,
    val x: Double,
    val y: Double,
    val dateTime:String,
    val assetId: Int,
    val floorMapId: Int

)
