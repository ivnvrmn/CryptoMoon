package com.rmnivnv.cryptomoon.model

import com.rmnivnv.cryptomoon.model.db.DBController

/**
 * Created by rmnivnv on 06/08/2017.
 */
class CoinsController(val dbController: DBController, val prefsProvider: PreferencesProvider) {

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

    fun getDisplayCoinsMap() = prefsProvider.getDisplayCoins()

    fun setDisplayCoins(coins: HashMap<String, ArrayList<String>>) = prefsProvider.setDisplayCoins(coins)

    fun deleteDisplayCoin(coin: DisplayCoin) {
        dbController.deleteDisplayCoin(coin)
        prefsProvider.deleteDisplayCoin(coin)
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