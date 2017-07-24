package com.rmnivnv.cryptomoon.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

/**
 * Created by rmnivnv on 02/07/2017.
 */

data class Market(val from: String, val to: String, val price: Int, val change: Double, val hold: Double, val logo: Int)

data class AllCoinsResponse(
        @SerializedName("Response") val response: String,
        @SerializedName("Message") val message: String,
        @SerializedName("BaseImageUrl") val baseImageUrl: String,
        @SerializedName("BaseLinkUrl") val baseLinkUrl: String,
        @SerializedName("Data") val data: JsonObject,
        @SerializedName("Type") val type: Int)

data class Coin(
        @SerializedName("Id") val id: String,
        @SerializedName("Url") val url: String,
        @SerializedName("ImageUrl") var imageUrl: String,
        @SerializedName("Name") val name: String,
        @SerializedName("CoinName") val coinName: String,
        @SerializedName("FullName") val fullName: String,
        @SerializedName("Algorithm") val algorithm: String,
        @SerializedName("ProofType") val proofType: String,
        @SerializedName("FullyPremined") val fullyPremined: String,
        @SerializedName("TotalCoinSupply") val totalCoinSupply: String,
        @SerializedName("PreMinedValue") val preMinedValue: String,
        @SerializedName("TotalCoinsFreeFloat") val totalCoinsFreeFloat: String,
        @SerializedName("SortOrder") val sortOrder: String)

data class CoinBodyRAW(
        val TYPE: String,
        val MARKET: String,
        val FROMSYMBOL: String,
        val TOSYMBOL: String,
        val FLAGS: String,
        val PRICE: Float,
        val LASTUPDATE: Float,
        val LASTVOLUME: Float,
        val LASTVOLUMETO: Float,
        val LASTTRADEID: Float,
        val VOLUME24HOUR: Float,
        val VOLUME24HOURTO: Float,
        val OPEN24HOUR: Float,
        val HIGH24HOUR: Float,
        val LOW24HOUR: Float,
        val LASTMARKET: String,
        val CHANGE24HOUR: Float,
        val CHANGEPCT24HOUR: Float,
        val SUPPLY: Float,
        val MKTCAP: Float)

@Entity(tableName = "coins_display")
data class CoinBodyDisplay(
        var from: String? = "",
        var to: String? = "",
        var imgUrl: String? = "",
        var FROMSYMBOL: String? = "",
        var TOSYMBOL: String? = "",
        var MARKET: String? = "",
        var PRICE: String? = "",
        var LASTUPDATE: String? = "",
        var LASTVOLUME: String? = "",
        var LASTVOLUMETO: String? = "",
        var LASTTRADEID: Double? = 0.0,
        var VOLUME24HOUR: String? = "",
        var VOLUME24HOURTO: String? = "",
        var OPEN24HOUR: String? = "",
        var HIGH24HOUR: String? = "",
        var LOW24HOUR: String? = "",
        var LASTMARKET: String? = "",
        var CHANGE24HOUR: String? = "",
        var CHANGEPCT24HOUR: String? = "",
        var SUPPLY: String? = "",
        var MKTCAP: String? = "")
{
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0
}
