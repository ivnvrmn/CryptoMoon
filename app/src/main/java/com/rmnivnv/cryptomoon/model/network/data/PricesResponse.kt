package com.rmnivnv.cryptomoon.model.network.data

import com.google.gson.annotations.SerializedName
import com.rmnivnv.cryptomoon.model.data.DisplayEntity
import com.rmnivnv.cryptomoon.model.data.RawEntity

data class PricesResponse(
    @SerializedName("RAW")
    val raw: Map<String, RawEntity>,
    @SerializedName("DISPLAY")
    val display: Map<String, DisplayEntity>
)
