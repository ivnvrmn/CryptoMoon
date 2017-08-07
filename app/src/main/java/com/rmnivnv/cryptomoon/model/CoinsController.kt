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

}