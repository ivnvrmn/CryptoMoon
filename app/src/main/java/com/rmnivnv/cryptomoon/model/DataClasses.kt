package com.rmnivnv.cryptomoon.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

/**
 * Created by rmnivnv on 02/07/2017.
 */

data class AllCoinsResponse(
        @SerializedName("Response") val response: String,
        @SerializedName("Message") val message: String,
        @SerializedName("BaseImageUrl") val baseImageUrl: String,
        @SerializedName("BaseLinkUrl") val baseLinkUrl: String,
        @SerializedName("Data") val data: JsonObject,
        @SerializedName("Type") val type: Int)

@Entity(tableName = "all_coins")
data class InfoCoin(
        @PrimaryKey @SerializedName("Id") var coinId: String,
        @SerializedName("Url") var url: String = "",
        @SerializedName("ImageUrl") var imageUrl: String = "",
        @SerializedName("Name") var name: String = "",
        @SerializedName("CoinName") var coinName: String = "",
        @SerializedName("FullName") var fullName: String = "",
        @SerializedName("Algorithm") var algorithm: String = "",
        @SerializedName("ProofType") var proofType: String = "",
        @SerializedName("FullyPremined") var fullyPremined: String = "",
        @SerializedName("TotalCoinSupply") var totalCoinSupply: String = "",
        @SerializedName("PreMinedValue") var preMinedValue: String = "",
        @SerializedName("TotalCoinsFreeFloat") var totalCoinsFreeFloat: String = "",
        @SerializedName("SortOrder") var sortOrder: String = "")

@Entity(tableName = "coins", primaryKeys = ["from_name", "to_name"])
data class Coin(
        @ColumnInfo(name = "from_name")
        var from: String = "",
        @ColumnInfo(name = "to_name")
        var to: String = "",
        var imgUrl: String = "",
        var fullName: String = "",
        var selected: Boolean = false,
        var fromSymbol: String = "",
        var toSymbol: String = "",
        var market: String = "",
        var price: String = "",
        var priceRaw: Float = 0f,
        var lastUpdate: String = "",
        var lastUpdateRaw: Float = 0f,
        var lastVolume: String = "",
        var lastVolumeRaw: Float = 0f,
        var lastVolumeTo: String = "",
        var lastVolumeToRaw: Float = 0f,
        var lastTradeId: Float = 0f,
        var volume24h: String = "",
        var volume24hRaw: Float = 0f,
        var volume24hTo: String = "",
        var volume24hToRaw: Float = 0f,
        var open24h: String = "",
        var open24hRaw: Float = 0f,
        var high24h: String = "",
        var high24hRaw: Float = 0f,
        var low24h: String = "",
        var low24hRaw: Float = 0f,
        var lastMarket: String = "",
        var change24h: String = "",
        var change24hRaw: Float = 0f,
        var changePct24h: String = "",
        var changePct24hRaw: Float = 0f,
        var supply: String = "",
        var supplyRaw: Float = 0f,
        var mktCap: String = "",
        var mktCapRaw: Float = 0f,
        var flags: String = "")

data class RawCoin(
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

@Entity(tableName = "display_coins", primaryKeys = arrayOf("from_name", "to_name"))
data class DisplayCoin(
        @ColumnInfo(name = "from_name")
        var from: String,
        @ColumnInfo(name = "to_name")
        var to: String,
        var imgUrl: String = "",
        var fullName: String = "",
        var selected: Boolean = false,
        var FROMSYMBOL: String = "",
        var TOSYMBOL: String = "",
        var MARKET: String = "",
        var PRICE: String = "",
        var LASTUPDATE: String = "",
        var LASTVOLUME: String = "",
        var LASTVOLUMETO: String = "",
        var LASTTRADEID: Double = 0.0,
        var VOLUME24HOUR: String = "",
        var VOLUME24HOURTO: String = "",
        var OPEN24HOUR: String = "",
        var HIGH24HOUR: String = "",
        var LOW24HOUR: String = "",
        var LASTMARKET: String = "",
        var CHANGE24HOUR: String = "",
        var CHANGEPCT24HOUR: String = "",
        var SUPPLY: String = "",
        var MKTCAP: String = "")

data class HistoData(
        val time: Long,
        val close: Float,
        val high: Float,
        val low: Float,
        val open: Float,
        @SerializedName("volumefrom") val volumeFrom: Float,
        @SerializedName("volumeto") val volumeTo: Float)

data class PairData(
        val exchange: String,
        val fromSymbol: String,
        val toSymbol: String,
        val volume24h: Float,
        val volume24hTo: Float)

@Entity(tableName = "top_coins")
data class TopCoinData(
        var id: String? = "",
        @PrimaryKey var name: String,
        var symbol: String? = "",
        var rank: Int? = 0,
        var price_usd: String? = "",
        var price_btc: String? = "",
        @SerializedName("24h_volume_usd") var vol24Usd: String? = "",
        var market_cap_usd: String? = "",
        var available_supply: String? = "",
        var total_supply: String? = "",
        var percent_change_1h: String? = "",
        var percent_change_24h: String? = "",
        var percent_change_7d: String? = "",
        var last_updated: String? = "",
        var imgUrl: String? = ""
)

@Entity(tableName = "holdings", primaryKeys = arrayOf("from_coin", "to_currency", "transaction_date"))
data class HoldingData(@ColumnInfo(name = "from_coin") var from: String,
                       @ColumnInfo(name = "to_currency") var to: String,
                       var quantity: Float = 0f,
                       var price: Float = 0f,
                       @ColumnInfo(name = "transaction_date") var date: Long)
