package com.rmnivnv.cryptomoon.model

import com.rmnivnv.cryptomoon.model.db.DBController

/**
 * Created by rmnivnv on 06/08/2017.
 */
class CoinsController(val dbController: DBController, val prefsProvider: PreferencesProvider) {

    fun getCoinsToDisplay(callback: GetDisplayCoinsCallback) {
        dbController.getDisplayCoins(object : GetDisplayCoinsCallback {
            override fun onSuccess(list: List<CoinBodyDisplay>) {
                callback.onSuccess(list)
            }

            override fun onError(t: Throwable) {
                callback.onError(t)
            }
        })
    }

    fun saveDisplayCoin(coin: CoinBodyDisplay) {
        dbController.saveDisplayCoin(coin)
    }

    fun getDisplayCoinsMap() = prefsProvider.getDisplayCoins()

    fun setDisplayCoins(coins: HashMap<String, ArrayList<String>>) = prefsProvider.setDisplayCoins(coins)

    fun deleteDisplayCoin(coin: CoinBodyDisplay) {
        dbController.deleteDisplayCoin(coin)
        prefsProvider.deleteDisplayCoin(coin)
    }

    fun getAllCoinsInfo(callback: GetAllCoinsFromDbCallback) {
        dbController.getAllCoinsInfo(object : GetAllCoinsFromDbCallback {
            override fun onSuccess(list: List<Coin>) {
                callback.onSuccess(list)
            }

            override fun onError(t: Throwable) {
                callback.onError(t)
            }
        })
    }

    fun saveAllCoinsInfo(allCoins: List<Coin>) {
        dbController.saveAllCoinsInfo(allCoins)
    }
}