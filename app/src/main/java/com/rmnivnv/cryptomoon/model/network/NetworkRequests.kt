package com.rmnivnv.cryptomoon.model.network

import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.utils.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by rmnivnv on 12/07/2017.
 */
class NetworkRequests(private val cryptoCompareAPI: CryptoCompareAPI) {

    fun getAllCoins(): Single<ArrayList<InfoCoin>>  {
        return cryptoCompareAPI.getCoinsList(COINS_LIST_URL)
                .subscribeOn(Schedulers.io())
                .map { getAllCoinsFromJson(it) }
    }

    fun getPrice(map: Map<String, ArrayList<String?>>): Single<ArrayList<Coin>> {
        return cryptoCompareAPI.getPrice(getQuery(map, FSYMS), getQuery(map, TSYMS))
                .subscribeOn(Schedulers.io())
                .map { getCoinsFromJson(it, map) }
    }

    private fun getQuery(map: Map<String, ArrayList<String?>>, type: String): String {
        var result = ""
        map.forEach { (key, value) ->
            if (key == type) value.forEach { result += """$it,""" }
        }
        if (result.isNotEmpty()) result = result.substring(0, result.length - 1)
        return result
    }

    fun getPairs(from: String): Single<ArrayList<PairData>> {
        return cryptoCompareAPI.getPairs(from)
                .subscribeOn(Schedulers.io())
                .map { getPairsListFromJson(it) }
    }

    fun getHistoPeriod(period: String, from: String?, to: String?): Single<ArrayList<HistoData>> {
        val histoPeriod: String
        var limit = 30
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
                aggregate = 2
            }
            WEEK -> {
                histoPeriod = HISTO_HOUR
                aggregate = 6
            }
            MONTH -> {
                histoPeriod = HISTO_DAY
            }
            MONTHS3 -> {
                histoPeriod = HISTO_DAY
                aggregate = 3
            }
            MONTHS6 -> {
                histoPeriod = HISTO_DAY
                aggregate = 6
            }
            else -> {
                histoPeriod = HISTO_DAY
                aggregate = 12
            }
        }

        return cryptoCompareAPI.getHistoPeriod(histoPeriod, from, to, limit, aggregate)
                .subscribeOn(Schedulers.io())
                .map { getHistoListFromJson(it) }
    }
}