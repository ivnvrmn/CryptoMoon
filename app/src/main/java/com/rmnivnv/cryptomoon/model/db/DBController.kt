package com.rmnivnv.cryptomoon.model.db

import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.data.TopCoinEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by rmnivnv on 05/08/2017.
 */
class DBController(val db: CMDatabase) {

//    fun saveCoin(coin: Coin) {
//        Single.fromCallable { db.myCoinsDao().insert(coin) }
//                .subscribeOn(Schedulers.io())
//                .subscribe()
//    }
//
//    fun saveCoinsList(list: List<Coin>) {
//        Single.fromCallable { db.myCoinsDao().insertList(list) }
//                .subscribeOn(Schedulers.io())
//                .subscribe()
//    }

//    fun getCoin(from: String, to: String) = db.myCoinsDao().getCoin(from, to)

//    fun deleteCoin(coin: Coin) {
//        Single.fromCallable { db.myCoinsDao().deleteCoin(coin) }
//                .subscribeOn(Schedulers.io())
//                .subscribe()
//    }
//
//    fun deleteCoins(coins: List<Coin>) {
//        Single.fromCallable { db.myCoinsDao().deleteCoins(coins) }
//                .subscribeOn(Schedulers.io())
//                .subscribe()
//    }

    fun saveAllCoinsInfo(allCoins: List<InfoCoin>) {
        Single.fromCallable { db.allCoinsDao().insertList(allCoins) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun saveTops(tops: List<TopCoinEntity>) {
        Single.fromCallable { db.topCoinsDao().insert(tops) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun clearTopCoins() {
        Single.fromCallable { db.topCoinsDao().clear() }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun saveHoldingData(holdingData: HoldingData) {
        Single.fromCallable { db.holdingsDao().insert(holdingData) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}