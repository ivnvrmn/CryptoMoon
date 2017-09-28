package com.rmnivnv.cryptomoon.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by rmnivnv on 12/07/2017.
 */

fun getCoinDisplayBodyFromJson(jsonObject: JsonObject, map: Map<String, ArrayList<String?>>): ArrayList<DisplayCoin> {
    val result: ArrayList<DisplayCoin> = ArrayList()
    val display: JsonElement
    val fromObjectsList: HashMap<String?, JsonElement> = HashMap()
    if (jsonObject.has(DISPLAY)) {
        display = jsonObject[DISPLAY]
        for ((key, value) in map) {
            if (key == FSYMS) {
                value.forEach {
                    if (display.asJsonObject.has(it)) {
                        fromObjectsList.put(it, display.asJsonObject[it])
                    }
                }
            }

        }
        for ((key, value) in map) {
            if (key == TSYMS) {
                value.forEach { toSymbol ->
                    for ((keyFrom, valueFrom) in fromObjectsList) {
                        if (valueFrom.asJsonObject.has(toSymbol)) {
                            val body: DisplayCoin = Gson().fromJson(valueFrom.asJsonObject[toSymbol], DisplayCoin::class.java)
                            body.from = keyFrom
                            body.to = toSymbol
                            result.add(body)
                        }
                    }
                }
            }
        }
    }
    return result
}

fun getAllCoinsFromJson(response: AllCoinsResponse): ArrayList<InfoCoin> {
    val result: ArrayList<InfoCoin> = ArrayList()
    val jsonObject = response.data
    jsonObject.entrySet().forEach {
        val coin = Gson().fromJson(it.value, InfoCoin::class.java)
        coin.imageUrl = response.baseImageUrl + coin.imageUrl
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

fun createCoinsMapWithCurrencies(coinsList: List<DisplayCoin>): HashMap<String, ArrayList<String?>> {
    val map: HashMap<String, ArrayList<String?>> = HashMap()
    val fromList: ArrayList<String?> = ArrayList()
    coinsList.forEach { fromList.add(it.from) }
    map.put(FSYMS, fromList)
    val toList: ArrayList<String?> = ArrayList()
    coinsList.forEach { toList.add(it.to) }
    map.put(TSYMS, toList)
    return map
}

fun doubleFromString(number: String) = NumberFormat.getInstance(Locale.getDefault()).parse(number).toDouble()

fun getChangeColor(change: Double) = when {
    change > 0 -> R.color.green
    change == 0.0 -> R.color.orange_dark
    else -> R.color.red
}

fun addCommasToStringNumber(number: String?): String {
    val formatter = DecimalFormat("#,###.00")
    return formatter.format(number?.toDouble())
}

fun getStringWithTwoDecimalsFromDouble(value: Double) = (Math.round(value * 100.0) / 100.0).toString()

fun formatLongDateToString(date: Long?, format: String): String = SimpleDateFormat(format, Locale.getDefault()).format(date)

fun getNumberSignByValue(value: Double) = if (value >= 0) "+" else "-"

fun getProfitLossText(change: Double, resProvider: ResourceProvider) = if (change >= 0) resProvider.getString(R.string.prf) else  resProvider.getString(R.string.ls)

fun getProfitLossTextBig(change: Double, resProvider: ResourceProvider) = if (change >= 0) resProvider.getString(R.string.profit_b) else  resProvider.getString(R.string.loss_b)

