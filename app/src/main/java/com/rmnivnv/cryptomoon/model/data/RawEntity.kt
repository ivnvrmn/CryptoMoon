package com.rmnivnv.cryptomoon.model.data

import android.arch.persistence.room.Embedded
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RawEntity(
    @Embedded
    @SerializedName("USD")
    val usd: RawCoinEntity
) : Parcelable