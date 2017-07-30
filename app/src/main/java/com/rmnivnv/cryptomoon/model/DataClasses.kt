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

@Entity(tableName = "all_coins")
data class Coin(
        @PrimaryKey @SerializedName("Id") var coinId: String? = "",
        @SerializedName("Url") var url: String? = "",
        @SerializedName("ImageUrl") var imageUrl: String? = "",
        @SerializedName("Name") var name: String? = "",
        @SerializedName("CoinName") var coinName: String? = "",
        @SerializedName("FullName") var fullName: String? = "",
        @SerializedName("Algorithm") var algorithm: String? = "",
        @SerializedName("ProofType") var proofType: String? = "",
        @SerializedName("FullyPremined") var fullyPremined: String? = "",
        @SerializedName("TotalCoinSupply") var totalCoinSupply: String? = "",
        @SerializedName("PreMinedValue") var preMinedValue: String? = "",
        @SerializedName("TotalCoinsFreeFloat") var totalCoinsFreeFloat: String? = "",
        @SerializedName("SortOrder") var sortOrder: String? = "")

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
