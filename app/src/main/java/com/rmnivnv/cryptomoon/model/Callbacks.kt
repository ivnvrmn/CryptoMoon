package com.rmnivnv.cryptomoon.model

/**
 * Created by rmnivnv on 12/07/2017.
 */

interface GetAllCoinsCallback {
    fun onSuccess(allCoins: ArrayList<InfoCoin>)
    fun onError(t: Throwable)
}

interface GetPriceCallback {
    fun onSuccess(coinsInfoList: ArrayList<DisplayCoin>?)
    fun onError(t: Throwable)
}

interface GetDisplayCoinsCallback {
    fun onSuccess(list: List<DisplayCoin>)
    fun onError(t: Throwable)
}

interface GetAllCoinsFromDbCallback {
    fun onSuccess(list: List<InfoCoin>)
    fun onError(t: Throwable)
}