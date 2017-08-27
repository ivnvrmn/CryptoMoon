package com.rmnivnv.cryptomoon.model.db

import com.rmnivnv.cryptomoon.model.InfoCoin
import com.rmnivnv.cryptomoon.model.DisplayCoin
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by rmnivnv on 05/08/2017.
 */
class DBController(val db: CMDatabase) {

    fun saveDisplayCoin(coin: DisplayCoin) {
        Single.fromCallable { db.displayCoinsDao().insert(coin) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun saveDisplayCoinsList(list: List<DisplayCoin>) {
        Single.fromCallable { db.displayCoinsDao().insertList(list) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun getDisplayCoin(from: String, to: String) = db.displayCoinsDao().getDisplayCoin(from, to)

    fun deleteDisplayCoin(coin: DisplayCoin) {
        Single.fromCallable { db.displayCoinsDao().deleteCoin(coin) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun deleteDisplayCoins(coins: List<DisplayCoin>) {
        Single.fromCallable { db.displayCoinsDao().deleteCoins(coins) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun saveAllCoinsInfo(allCoins: List<InfoCoin>) {
        Single.fromCallable { db.allCoinsDao().insertList(allCoins) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}