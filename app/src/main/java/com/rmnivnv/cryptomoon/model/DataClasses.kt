package com.rmnivnv.cryptomoon.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

/**
 * Created by rmnivnv on 02/07/2017.
 */

data class Market(val from: String, val to: String, val price: Int, val change: Double, val hold: Double, val logo: Int)

data class AllCoinsResponse(@SerializedName("Response") val response: String,
                            @SerializedName("Message") val message: String,
                            @SerializedName("BaseImageUrl") val baseImageUrl: String,
                            @SerializedName("BaseLinkUrl") val baseLinkUrl: String,
                            @SerializedName("Data") val data: JsonObject,
                            @SerializedName("Type") val type: Int)

data class Coin(@SerializedName("Id") val id: String,
                @SerializedName("Url") val url: String,
                @SerializedName("ImageUrl") val imageUrl: String,
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

data class PriceBodyRAW(val TYPE: String,
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

data class PriceBodyDisplay(val FROMSYMBOL: String?,
                            val TOSYMBOL: String?,
                            val MARKET: String?,
                            val PRICE: String?,
                            val LASTUPDATE: String?,
                            val LASTVOLUME: String?,
                            val LASTVOLUMETO: String?,
                            val LASTTRADEID: Double?,
                            val VOLUME24HOUR: String?,
                            val VOLUME24HOURTO: String?,
                            val OPEN24HOUR: String?,
                            val HIGH24HOUR: String?,
                            val LOW24HOUR: String?,
                            val LASTMARKET: String?,
                            val CHANGE24HOUR: String?,
                            val CHANGEPCT24HOUR: String?,
                            val SUPPLY: String?,
                            val MKTCAP: String?)
