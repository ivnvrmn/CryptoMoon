package com.rmnivnv.cryptomoon.model.data

import android.arch.persistence.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class RawCoinEntity(
    @SerializedName("TYPE")
    val type: String,
    @SerializedName("MARKET")
    val market: String,
    @SerializedName("FROMSYMBOL")
    @ColumnInfo(name = "from_symbol")
    val fromSymbol: String,
    @SerializedName("TOSYMBOL")
    @ColumnInfo(name = "to_symbol")
    val toSymbol: String,
    @SerializedName("FLAGS")
    val flags: String,
    @SerializedName("PRICE")
    val price: Float,
    @SerializedName("LASTUPDATE")
    val lastUpdate: Long,
    @SerializedName("LASTVOLUME")
    val lastVolume: Float,
    @SerializedName("LASTVOLUMETO")
    val lastVolumeTo: Float,
    @SerializedName("LASTTRADEID")
    val lastTradeId: String,
    @SerializedName("VOLUMEDAY")
    val volumeDay: Float,
    @SerializedName("VOLUMEDAYTO")
    val volumeDayTo: Float,
    @SerializedName("VOLUME24HOUR")
    val volume24Hour: Float,
    @SerializedName("VOLUME24HOURTO")
    val volume24HourTo: Float,
    @SerializedName("OPENDAY")
    val openDay: Float,
    @SerializedName("HIGHDAY")
    val highDay: Float,
    @SerializedName("LOWDAY")
    val lowDay: Float,
    @SerializedName("OPEN24HOUR")
    val open24Hour: Float,
    @SerializedName("HIGH24HOUR")
    val high24Hour: Float,
    @SerializedName("LOW24HOUR")
    val low24Hour: Float,
    @SerializedName("LASTMARKET")
    val lastMarket: String,
    @SerializedName("VOLUMEHOUR")
    val volumeHour: Float,
    @SerializedName("VOLUMEHOURTO")
    val volumeHourTo: Float,
    @SerializedName("OPENHOUR")
    val openHour: Float,
    @SerializedName("HIGHHOUR")
    val highHour: Float,
    @SerializedName("LOWHOUR")
    val lowHour: Float,
    @SerializedName("CHANGE24HOUR")
    val change24Hour: Float,
    @SerializedName("CHANGEPCT24HOUR")
    val changePct24Hour: Float,
    @SerializedName("CHANGEDAY")
    val changeDay: Float,
    @SerializedName("CHANGEPCTDAY")
    val changePctDay: Float,
    @SerializedName("SUPPLY")
    val supply: Float,
    @SerializedName("MKTCAP")
    val marketCap: Float,
    @SerializedName("TOTALVOLUME24H")
    val totalVolume24Hour: Float,
    @SerializedName("TOTALVOLUME24HTO")
    val totalVolume24HourTo: Float,
    @SerializedName("IMAGEURL")
    val imageUrl: String
)