package com.rmnivnv.cryptomoon.model.db

import com.rmnivnv.cryptomoon.model.Coin
import com.rmnivnv.cryptomoon.model.CoinBodyDisplay
import com.rmnivnv.cryptomoon.model.GetAllCoinsFromDbCallback
import com.rmnivnv.cryptomoon.model.GetDisplayCoinsCallback
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by rmnivnv on 05/08/2017.
 */
class DBController(val db: CMDatabase) {

    fun saveDisplayCoin(coin: CoinBodyDisplay) {
        Single.fromCallable { db.displayCoinsDao().insert(coin) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun getDisplayCoins(callback: GetDisplayCoinsCallback) {
        db.displayCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ callback.onSuccess(it) }, { callback.onError(it) })
    }

    fun deleteDisplayCoin(coin: CoinBodyDisplay) {
        Single.fromCallable { db.displayCoinsDao().deleteCoin(coin) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun saveAllCoinsInfo(allCoins: List<Coin>) {
        Single.fromCallable { db.allCoinsDao().insertList(allCoins) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun getAllCoinsInfo(callback: GetAllCoinsFromDbCallback) {
        db.allCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ callback.onSuccess(it) }, { callback.onError(it) })
    }
}