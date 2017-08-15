package com.rmnivnv.cryptomoon.model

import com.rmnivnv.cryptomoon.model.db.DBController

/**
 * Created by rmnivnv on 06/08/2017.
 */
class CoinsController(private val dbController: DBController) {

    fun getCoinsToDisplay(callback: GetDisplayCoinsCallback) {
        dbController.getDisplayCoins(object : GetDisplayCoinsCallback {
            override fun onSuccess(list: List<DisplayCoin>) {
                callback.onSuccess(list)
            }

            override fun onError(t: Throwable) {
                callback.onError(t)
            }
        })
    }

    fun saveDisplayCoin(coin: DisplayCoin) {
        dbController.saveDisplayCoin(coin)
    }

    fun saveDisplayCoinList(list: List<DisplayCoin>) {
        dbController.saveDisplayCoinsList(list)
    }

    fun getDisplayCoinsMap(callback: GetDisplayCoinsMapFromDbCallback) {
        val map: HashMap<String, ArrayList<String>> = HashMap()
        val toList: ArrayList<String> = ArrayList()
        toList.add(USD)
        val fromList: ArrayList<String> = ArrayList()
        dbController.getDisplayCoins(object : GetDisplayCoinsCallback {
            override fun onSuccess(list: List<DisplayCoin>) {
                if (list.isNotEmpty()) {
                    list.forEach {
                        fromList.add(it.from)
                    }
                    map.put(FSYMS, fromList)
                    map.put(TSYMS, toList)
                }
                callback.onSuccess(map)
            }

            override fun onError(t: Throwable) {
                callback.onError(t)
            }
        })
    }

    fun deleteDisplayCoin(coin: DisplayCoin) {
        dbController.deleteDisplayCoin(coin)
    }

    fun getAllCoinsInfo(callback: GetAllCoinsFromDbCallback) {
        dbController.getAllCoinsInfo(object : GetAllCoinsFromDbCallback {
            override fun onSuccess(list: List<InfoCoin>) {
                callback.onSuccess(list)
            }

            override fun onError(t: Throwable) {
                callback.onError(t)
            }
        })
    }

    fun saveAllCoinsInfo(allCoins: List<InfoCoin>) {
        dbController.saveAllCoinsInfo(allCoins)
    }
}