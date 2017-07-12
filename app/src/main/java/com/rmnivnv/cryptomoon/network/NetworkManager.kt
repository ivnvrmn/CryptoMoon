package com.rmnivnv.cryptomoon.network

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.utils.getPriceDisplayBodyFromJson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by rmnivnv on 12/07/2017.
 */
class NetworkManager(val api: CryptoCompareAPI) {

//    fun getAllCoins(callback: GetAllCoinsCallback): Disposable  {
//        return api.getCoinsList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe( { callback.onSuccess(it) }, { callback.onError(it) } )
//    }

    fun getPrice(from: String, to: String, callback: GetPriceCallback): Disposable {
        return api.getPrice(from, to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { getPriceDisplayBodyFromJson(it, from, to) }
                .subscribe( { callback.onSuccess(it) }, { callback.onError(it) } )
    }

}