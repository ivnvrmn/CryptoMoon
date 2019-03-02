package com.rmnivnv.cryptomoon.model.network.data

import com.google.gson.annotations.SerializedName
import com.rmnivnv.cryptomoon.model.data.ConversionType
import com.rmnivnv.cryptomoon.model.data.HistoricalEntity

data class HistoricalDataResponse(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Type")
    val type: Int,
    @SerializedName("Aggregated")
    val aggregated: Boolean,
    @SerializedName("Data")
    val data: List<HistoricalEntity>,
    @SerializedName("TimeTo")
    val timeTo: Long,
    @SerializedName("TimeFrom")
    val timeFrom: Long,
    @SerializedName("FirstValueInArray")
    val firstValueInArray: Boolean,
    @SerializedName("ConversionType")
    val conversionType: ConversionType,
    @SerializedName("HasWarning")
    val hasWarning: Boolean
)