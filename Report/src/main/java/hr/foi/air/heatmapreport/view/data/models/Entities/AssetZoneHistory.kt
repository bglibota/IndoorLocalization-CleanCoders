package hr.foi.air.heatmapreport.view.data.models.Entities

import java.time.LocalDateTime
import java.time.Duration

data class AssetZoneHistory(
    val id: Int,
    val enterDateTime: String?,
    val exitDateTime: String?,
    val retentionTime: String?,
    val assetId: Int?,
    val zoneId: Int?
)
