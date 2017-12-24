package com.rmnivnv.cryptomoon.model.db

import com.rmnivnv.cryptomoon.model.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by rmnivnv on 05/08/2017.
 */
class DBController(val db: CMDatabase) {

    fun saveCoin(coin: Coin) {
        Single.fromCallable { db.coinsDao().insert(coin) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun saveCoinsList(list: List<Coin>) {
        Single.fromCallable { db.coinsDao().insertList(list) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun getCoin(from: String, to: String) = db.coinsDao().getCoin(from, to)

    fun deleteCoin(coin: Coin) {
        Single.fromCallable { db.coinsDao().deleteCoin(coin) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun deleteCoins(coins: List<Coin>) {
        Single.fromCallable { db.coinsDao().deleteCoins(coins) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun saveAllCoinsInfo(allCoins: List<InfoCoin>) {
        Single.fromCallable { db.allCoinsDao().insertList(allCoins) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun saveTopCoinsList(list: List<TopCoinData>) {
        Single.fromCallable { db.topCoinsDao().insertTopCoinsList(list) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun saveHoldingData(holdingData: HoldingData) {
        Single.fromCallable { db.holdingsDao().insert(holdingData) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}