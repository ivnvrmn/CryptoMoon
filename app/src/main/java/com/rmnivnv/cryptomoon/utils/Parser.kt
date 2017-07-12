package com.rmnivnv.cryptomoon.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.rmnivnv.cryptomoon.model.DISPLAY
import com.rmnivnv.cryptomoon.model.PriceBodyDisplay

/**
 * Created by rmnivnv on 12/07/2017.
 */

fun getPriceDisplayBodyFromJson(jsonObject: JsonObject, from: String, to: String): PriceBodyDisplay {
    val display: JsonElement
    val fromObject: JsonElement
    if (jsonObject.has(DISPLAY)) {
        display = jsonObject[DISPLAY]
        if (display.asJsonObject.has(from)) {
            fromObject = display.asJsonObject[from]
            if ((fromObject.asJsonObject.has(to))) {
                return Gson().fromJson(fromObject.asJsonObject[to], PriceBodyDisplay::class.java)
            }
        }
    }
    return PriceBodyDisplay(null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null)
}