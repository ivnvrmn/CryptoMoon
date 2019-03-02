package com.rmnivnv.cryptomoon.model.data

import com.google.gson.annotations.SerializedName

data class ConversionType(
    @SerializedName("type")
    val type: String,
    @SerializedName("conversionSymbol")
    val conversionSymbol: String
)