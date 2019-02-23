package com.rmnivnv.cryptomoon.model.network.data

import com.google.gson.annotations.SerializedName
import com.rmnivnv.cryptomoon.model.data.TopCoinEntity

data class TopCoinsResponse(
    @SerializedName("Message")
    val message: String,
    @SerializedName("Type")
    val type: Int,
    @SerializedName("SponsoredData")
    val sponsoredData: List<String>,
    @SerializedName("Data")
    val data: List<TopCoinEntity>,
    @SerializedName("HasWarning")
    val hasWarning: Boolean
)