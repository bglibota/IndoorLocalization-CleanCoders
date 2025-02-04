package com.example.indoorlocalizationcleancoders.data.models

data class TrackedObject(
    val AssetName: String,
    val X: Float,
    val Y: Float,
    val AssetId: Int,
    val FloorMapId: Int
)
