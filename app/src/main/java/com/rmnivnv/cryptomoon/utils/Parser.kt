package com.rmnivnv.cryptomoon.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by rmnivnv on 12/07/2017.
 */

fun getCoinsFromJson(jsonObject: JsonObject, map: Map<String, ArrayList<String?>>): ArrayList<Coin> {
    val result: ArrayList<Coin> = ArrayList()
    var displayJson: JsonElement? = null
    if (jsonObject.has(DISPLAY)) displayJson = jsonObject[DISPLAY]
    var rawJson: JsonElement? = null
    if (jsonObject.has(RAW)) rawJson = jsonObject[RAW]
    if (displayJson != null && rawJson != null) {
        map[FSYMS]?.forEach {
            if (it != null) {
                try {
                    val displayCoin: DisplayCoin = Gson().fromJson(displayJson.asJsonObject[it].asJsonObject[USD],
                            DisplayCoin::class.java)
                    val rawCoin: RawCoin = Gson().fromJson(rawJson.asJsonObject[it].asJsonObject[USD],
                            RawCoin::class.java)
                    val coin = Coin(
                            from = it,
                            to = USD,
                            fromSymbol = displayCoin.FROMSYMBOL,
                            toSymbol = displayCoin.TOSYMBOL,
                            market = displayCoin.MARKET,
                            price = displayCoin.PRICE,
                            priceRaw = rawCoin.PRICE,
                            lastUpdate = displayCoin.LASTUPDATE,
                            lastUpdateRaw = rawCoin.LASTUPDATE,
                            lastVolume = displayCoin.LASTVOLUME,
                            lastVolumeRaw = rawCoin.LASTVOLUME,
                            lastVolumeTo = displayCoin.LASTVOLUMETO,
                            lastVolumeToRaw = rawCoin.LASTVOLUMETO,
                            lastTradeId = rawCoin.LASTTRADEID,
                            volume24h = displayCoin.VOLUME24HOUR,
                            volume24hRaw = rawCoin.VOLUME24HOUR,
                            volume24hTo = displayCoin.VOLUME24HOURTO,
                            volume24hToRaw = rawCoin.VOLUME24HOURTO,
                            open24h = displayCoin.OPEN24HOUR,
                            open24hRaw = rawCoin.OPEN24HOUR,
                            high24h = displayCoin.HIGH24HOUR,
                            high24hRaw = rawCoin.HIGH24HOUR,
                            low24h = displayCoin.LOW24HOUR,
                            low24hRaw = rawCoin.LOW24HOUR,
                            lastMarket = displayCoin.LASTMARKET,
                            change24h = displayCoin.CHANGE24HOUR,
                            change24hRaw = rawCoin.CHANGE24HOUR,
                            changePct24h = displayCoin.CHANGEPCT24HOUR,
                            changePct24hRaw = rawCoin.CHANGEPCT24HOUR,
                            supply = displayCoin.SUPPLY,
                            supplyRaw = rawCoin.SUPPLY,
                            mktCap = displayCoin.MKTCAP,
                            mktCapRaw = rawCoin.MKTCAP,
                            flags = rawCoin.FLAGS)
                    result.add(coin)
                } catch (ex: Exception) {
                    println(ex)
                    return result
                }
            }
        }
    }
    return result
}

fun getAllCoinsFromJson(data: AllCoinsData): ArrayList<InfoCoin> {
    val result: ArrayList<InfoCoin> = ArrayList()
    val jsonObject = data.data
    jsonObject.entrySet().forEach {
        val coin = Gson().fromJson(it.value, InfoCoin::class.java)
        coin.imageUrl = data.baseImageUrl + coin.imageUrl
        result.add(coin)
    }
    return result
}

fun getHistoListFromJson(jsonObject: JsonObject): ArrayList<HistoData> {
    val result: ArrayList<HistoData> = ArrayList()
    if (jsonObject.has(DATA)) {
        jsonObject.getAsJsonArray(DATA).forEach {
            result.add(Gson().fromJson(it, HistoData::class.java))
        }
    }
    return result
}

fun getPairsListFromJson(jsonObject: JsonObject): ArrayList<PairData> {
    val result: ArrayList<PairData> = ArrayList()
    if (jsonObject.has(DATA)) {
        jsonObject.getAsJsonArray(DATA).forEach {
            result.add(Gson().fromJson(it, PairData::class.java))
        }
    }
    return result
}

fun createCoinsMapWithCurrencies(coinsList: List<Coin>): HashMap<String, ArrayList<String?>> {
    val map: HashMap<String, ArrayList<String?>> = HashMap()
    val fromList: ArrayList<String?> = ArrayList()
    coinsList.forEach { fromList.add(it.from) }
    map.put(FSYMS, fromList)
    val toList: ArrayList<String?> = ArrayList()
    coinsList.forEach { toList.add(it.to) }
    map.put(TSYMS, toList)
    return map
}

fun getChangeColor(change: Float) = when {
    change > 0 -> R.color.green
    change == 0f -> R.color.orange_dark
    else -> R.color.red
}

fun addCommasToStringNumber(number: String?): String {
    val formatter = DecimalFormat("#,###.00")
    return formatter.format(number?.toDouble())
}

fun getStringWithTwoDecimalsFromDouble(value: Float) = (Math.round(value * 100.0) / 100.0).toString()

fun formatLongDateToString(date: Long?, format: String): String = SimpleDateFormat(format, Locale.getDefault()).format(date)

fun getNumberSignByValue(value: Double) = if (value >= 0) "+" else "-"

fun getProfitLossText(change: Float, resProvider: ResourceProvider) = if (change >= 0) resProvider.getString(R.string.prf) else  resProvider.getString(R.string.ls)

fun getProfitLossTextBig(change: Float, resProvider: ResourceProvider) =
        if (change >= 0) resProvider.getString(R.string.profit_b)
        else  resProvider.getString(R.string.loss_b)

