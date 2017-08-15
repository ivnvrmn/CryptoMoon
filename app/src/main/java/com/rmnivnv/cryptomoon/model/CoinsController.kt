package com.rmnivnv.cryptomoon.model

import com.rmnivnv.cryptomoon.model.db.DBController

/**
 * Created by rmnivnv on 06/08/2017.
 */
class CoinsController(private val dbController: DBController) {

    private var allCoins: List<InfoCoin> = mutableListOf()

    fun saveDisplayCoin(coin: DisplayCoin) {
        if (allCoins.isNotEmpty()) {
            addImageUrlToCoin(coin)
            addFullNameToCoin(coin)
        }
        dbController.saveDisplayCoin(coin)
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
                addImageUrlToCoin(it)
                addFullNameToCoin(it)
            }
        }
        dbController.saveDisplayCoinsList(list)
    }

    fun deleteDisplayCoin(coin: DisplayCoin) {
        dbController.deleteDisplayCoin(coin)
    }

    fun saveAllCoinsInfo(allCoins: List<InfoCoin>) {
        this.allCoins = allCoins
        dbController.saveAllCoinsInfo(allCoins)
    }
}