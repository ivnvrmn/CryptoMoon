package com.rmnivnv.cryptomoon.model

import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.db.DBController
import io.reactivex.schedulers.Schedulers

/**
 * Created by rmnivnv on 06/08/2017.
 */
class CoinsController(private val dbController: DBController, private val db: CMDatabase) {

    init {
        db.allCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .subscribe({ allCoins = it })
    }

    private var allCoins: List<InfoCoin> = mutableListOf()

    fun saveDisplayCoin(coin: DisplayCoin) {
        if (allCoins.isNotEmpty()) {
            addAdditionalInfo(coin)
        }
        dbController.saveDisplayCoin(coin)
    }

    private fun addAdditionalInfo(coin: DisplayCoin) {
        addImageUrlToCoin(coin)
        addFullNameToCoin(coin)
    }

    private fun addImageUrlToCoin(coin: DisplayCoin) {
        coin.imgUrl = allCoins.find { it.name == coin.from }?.imageUrl ?: ""
    }

    private fun addFullNameToCoin(coin: DisplayCoin) {
        coin.fullName = allCoins.find { it.name == coin.from }?.coinName ?: ""
    }

    fun saveDisplayCoinList(list: List<DisplayCoin>) {
        if (list.isNotEmpty() && allCoins.isNotEmpty()) {
            list.forEach {
                addAdditionalInfo(it)
            }
        }
        dbController.saveDisplayCoinsList(list)
    }

    fun deleteDisplayCoin(coin: DisplayCoin) = dbController.deleteDisplayCoin(coin)

    fun deleteDisplayCoins(coins: List<DisplayCoin>) = dbController.deleteDisplayCoins(coins)

    fun saveAllCoinsInfo(allCoins: List<InfoCoin>) {
        dbController.saveAllCoinsInfo(allCoins)
    }

    fun getDisplayCoin(from: String, to: String) = dbController.getDisplayCoin(from, to)
}