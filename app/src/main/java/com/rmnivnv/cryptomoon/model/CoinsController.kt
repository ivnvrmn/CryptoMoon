package com.rmnivnv.cryptomoon.model

import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.db.DBController
import io.reactivex.schedulers.Schedulers

/**
 * Created by rmnivnv on 06/08/2017.
 */
class CoinsController(private val dbController: DBController, db: CMDatabase) {

    init {
        db.allCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .subscribe({ allInfoCoins = it })
        db.coinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .subscribe({ allCoins = it })
    }

    private var allInfoCoins: List<InfoCoin> = mutableListOf()
    private var allCoins: List<Coin> = mutableListOf()

    fun saveCoin(coin: Coin) {
        if (allInfoCoins.isNotEmpty()) {
            addAdditionalInfoToCoin(coin)
        }
        dbController.saveCoin(coin)
    }

    fun addAdditionalInfoToCoin(coin: Coin) {
        addImageUrlToCoin(coin)
        addFullNameToCoin(coin)
    }

    private fun addImageUrlToCoin(coin: Coin) {
        coin.imgUrl = allInfoCoins.find { it.name == coin.from }?.imageUrl ?: ""
    }

    private fun addFullNameToCoin(coin: Coin) {
        coin.fullName = allInfoCoins.find { it.name == coin.from }?.coinName ?: ""
    }

    fun saveCoinsList(list: List<Coin>) {
        if (list.isNotEmpty() && allInfoCoins.isNotEmpty()) {
            list.forEach {
                addAdditionalInfoToCoin(it)
            }
        }
        dbController.saveCoinsList(list)
    }

    fun deleteCoin(coin: Coin) = dbController.deleteCoin(coin)

    fun deleteCoins(coins: List<Coin>) = dbController.deleteCoins(coins)

    fun saveAllCoinsInfo(allCoins: List<InfoCoin>) {
        dbController.saveAllCoinsInfo(allCoins)
    }

    fun getCoin(from: String, to: String) = dbController.getCoin(from, to)

    fun saveTopCoinsList(list: List<TopCoinData>) {
        list.forEach { coin ->
            coin.imgUrl = allInfoCoins.find { it.name == coin.symbol }?.imageUrl ?: ""
        }
        dbController.saveTopCoinsList(list)
    }

    fun coinIsAdded(coin: TopCoinData): Boolean {
        if (allCoins.isNotEmpty()) {
            return allCoins.find { it.from == coin.symbol && it.to == USD } != null
        }
        return false
    }

    fun coinAlreadyAdded(coin: String) = allCoins.find { it.from == coin } != null

    fun allInfoCoinsIsEmpty() = allInfoCoins.isEmpty()
}