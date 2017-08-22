package com.rmnivnv.cryptomoon.network

import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.utils.getAllCoinsFromJson
import com.rmnivnv.cryptomoon.utils.getCoinDisplayBodyFromJson
import com.rmnivnv.cryptomoon.utils.getHistoListFromJson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by rmnivnv on 12/07/2017.
 */
class NetworkRequests(val api: CryptoCompareAPI) {

    fun getAllCoins(callback: GetAllCoinsCallback): Disposable  {
        return api.getCoinsList(COINS_LIST_URL)
                .subscribeOn(Schedulers.io())
                .map { getAllCoinsFromJson(it) }
                .subscribe({ callback.onSuccess(it) }, { callback.onError(it) })
    }

    fun getPrice(map: Map<String, ArrayList<String>>, callback: GetPriceCallback): Disposable {
        return api.getPrice(getQuery(map, FSYMS), getQuery(map, TSYMS))
                .subscribeOn(Schedulers.io())
                .map { getCoinDisplayBodyFromJson(it, map) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ callback.onSuccess(it) }, { callback.onError(it) })
    }

    fun getHistoPeriod(period: String, from: String, to: String, callback: GetHistoCallback): Disposable {
        val histoPeriod: String
        val limit: Int
        var aggregate = 1
        when (period) {
            HOUR -> {
                histoPeriod = HISTO_MINUTE
                limit = 60
                aggregate = 2
            }
            HOURS12 -> {
                histoPeriod = HISTO_HOUR
                limit = 12
            }
            HOURS24 -> {
                histoPeriod = HISTO_HOUR
                limit = 24
            }
            DAYS3 -> {
                histoPeriod = HISTO_HOUR
                limit = 72
                aggregate = 2
            }
            WEEK -> {
                histoPeriod = HISTO_HOUR
                limit = 168
                aggregate = 3
            }
            MONTH -> {
                histoPeriod = HISTO_DAY
                limit = 30
            }
            MONTHS3 -> {
                histoPeriod = HISTO_DAY
                limit = 90
                aggregate = 2
            }
            MONTHS6 -> {
                histoPeriod = HISTO_DAY
                limit = 180
                aggregate = 4
            }
            else -> {
                histoPeriod = HISTO_DAY
                limit = 365
                aggregate = 10
            }
        }

        return api.getHistoPeriod(histoPeriod, from, to, limit, aggregate)
                .subscribeOn(Schedulers.io())
                .map { getHistoListFromJson(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ callback.onSuccess(it) }, { callback.onError(it) })
    }

    private fun getQuery(map: Map<String, ArrayList<String>>, type: String): String {
        var result = ""
        map.forEach { (key, value) ->
            if (key == type) value.forEach { result += """$it,""" }
        }
        if (result.isNotEmpty()) result = result.substring(0, result.length - 1)
        return result
    }

}