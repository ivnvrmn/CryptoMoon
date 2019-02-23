package com.rmnivnv.cryptomoon.model.data

import android.arch.persistence.room.Embedded
import com.google.gson.annotations.SerializedName

data class RawEntity(
    @Embedded
    @SerializedName("USD")
    val usd: RawCoinEntity
)