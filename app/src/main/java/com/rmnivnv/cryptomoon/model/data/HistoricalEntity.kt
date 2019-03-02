package com.rmnivnv.cryptomoon.model.data

import com.google.gson.annotations.SerializedName

data class HistoricalEntity(
    @SerializedName("time")
    val time: Long,
    @SerializedName("close")
    val close: Float,
    @SerializedName("high")
    val high: Float,
    @SerializedName("low")
    val low: Float,
    @SerializedName("open")
    val open: Float,
    @SerializedName("volumefrom")
    val volumeFrom: Float,
    @SerializedName("volumeto")
    val volumeTo: Float
)