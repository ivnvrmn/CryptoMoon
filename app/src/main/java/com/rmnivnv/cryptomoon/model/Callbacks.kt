package com.rmnivnv.cryptomoon.model

/**
 * Created by rmnivnv on 12/07/2017.
 */

interface GetAllCoinsCallback {
    fun onSuccess(allCoins: ArrayList<Coin>)
    fun onError(t: Throwable)
}

interface GetPriceCallback {
    fun onSuccess(coinsInfoList: ArrayList<CoinBodyDisplay>?)
    fun onError(t: Throwable)
}