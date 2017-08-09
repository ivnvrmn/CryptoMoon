package com.rmnivnv.cryptomoon.model.db

import com.rmnivnv.cryptomoon.model.InfoCoin
import com.rmnivnv.cryptomoon.model.DisplayCoin
import com.rmnivnv.cryptomoon.model.GetAllCoinsFromDbCallback
import com.rmnivnv.cryptomoon.model.GetDisplayCoinsCallback
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
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

    fun getDisplayCoins(callback: GetDisplayCoinsCallback) {
        db.displayCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ callback.onSuccess(it) }, { callback.onError(it) })
    }

    fun deleteDisplayCoin(coin: DisplayCoin) {
        Single.fromCallable { db.displayCoinsDao().deleteCoin(coin) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun saveAllCoinsInfo(allCoins: List<InfoCoin>) {
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