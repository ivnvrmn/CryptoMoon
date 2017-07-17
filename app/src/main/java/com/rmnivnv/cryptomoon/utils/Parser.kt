package com.rmnivnv.cryptomoon.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.rmnivnv.cryptomoon.model.DISPLAY
import com.rmnivnv.cryptomoon.model.FSYMS
import com.rmnivnv.cryptomoon.model.PriceBodyDisplay
import com.rmnivnv.cryptomoon.model.TSYMS

/**
 * Created by rmnivnv on 12/07/2017.
 */

fun getPriceDisplayBodyFromJson(jsonObject: JsonObject, map: Map<String, ArrayList<String>>): ArrayList<PriceBodyDisplay> {
    val result: ArrayList<PriceBodyDisplay> = ArrayList()
    val display: JsonElement
    val fromObjectsList: HashMap<String, JsonElement> = HashMap()
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
                            val body: PriceBodyDisplay = Gson().fromJson(valueFrom.asJsonObject[toSymbol], PriceBodyDisplay::class.java)
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