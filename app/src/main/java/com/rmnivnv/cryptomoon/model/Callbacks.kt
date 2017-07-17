package com.rmnivnv.cryptomoon.model

import com.google.gson.JsonObject

/**
 * Created by rmnivnv on 12/07/2017.
 */

interface GetAllCoinsCallback {
    fun onSuccess(response: AllCoinsResponse)
    fun onError(t: Throwable)
}

interface GetPriceCallback {
    fun onSuccess(coinsInfoList: ArrayList<PriceBodyDisplay>)
    fun onError(t: Throwable)
}