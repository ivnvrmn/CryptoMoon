package com.rmnivnv.cryptomoon.model.data

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

sealed class Coin{
    abstract val raw: RawEntity
    abstract val display: DisplayEntity
}

@Entity(tableName = "top_coins")
data class TopCoinEntity(
    @PrimaryKey
    @Embedded(prefix = "raw")
    @SerializedName("RAW")
    override val raw: RawEntity,
    @Embedded(prefix = "display")
    @SerializedName("DISPLAY")
    override val display: DisplayEntity,
    @Embedded(prefix = "coin_info")
    @SerializedName("CoinInfo")
    val coinInfo: CoinInfo
) : Coin()

@Parcelize
@Entity(tableName = "my_coins")
data class CoinEntity(
    @PrimaryKey
    @Embedded(prefix = "raw")
    @SerializedName("RAW")
    override val raw: RawEntity,
    @Embedded(prefix = "display")
    @SerializedName("DISPLAY")
    override val display: DisplayEntity
) : Coin(), Parcelable

