package com.rmnivnv.cryptomoon.model

import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.utils.doubleFromString
import io.reactivex.schedulers.Schedulers

/**
 * Created by rmnivnv on 09/09/2017.
 */
class HoldingsHandler(db: CMDatabase) {

    init {
        db.displayCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .subscribe({ displayCoins = it })
        db.holdingsDao().getAllHoldings()
                .subscribeOn(Schedulers.io())
                .subscribe({ holdings = it })
    }

    private var displayCoins: List<DisplayCoin> = arrayListOf()
    private var holdings: List<HoldingData> = arrayListOf()

    fun getTotalChangePercent(): Double {
        val oldValue = getTotalValueWithTradePrice()
        var newValue = 0.0

        holdings.forEach {
            val quantity = it.quantity
            val fromList = displayCoins.filter { (from) -> it.from == from }
            fromList.forEach {
                if (it.to == USD) {
                    newValue += quantity * doubleFromString(it.PRICE.substring(2))
                } else {
                    //todo calculate if not USD
                }
            }
        }
        return calculateChangePercent(oldValue, newValue)
    }

    private fun getTotalValueWithTradePrice(): Double {
        val sums: ArrayList<Double> = arrayListOf()
        holdings.forEach { sums.add(it.quantity * it.price) }
        return sums.sum()
    }

    private fun calculateChangePercent(value1: Double, value2: Double) =
            if (value1 > value1) (value2 - value1) / value2 * 100
            else (value2 - value1) / value1 * 100

    fun getTotalChangeValue() = getTotalValueWithCurrentPrice() - getTotalValueWithTradePrice()

    fun getTotalValueWithCurrentPrice(): Double {
        val sums: ArrayList<Double> = arrayListOf()
        holdings.forEach { (from, _, quantity) ->
            val currentPrice = displayCoins.find { it.from == from }?.PRICE
            if (currentPrice != null) {
                sums.add(quantity * doubleFromString(currentPrice.substring(2)))
            }
        }
        return sums.sum()
    }

    fun getTotalValueWithCurrentPriceByHoldingData(holdingData: HoldingData): Double {
        val currentPrice = displayCoins.find { it.from == holdingData.from && it.to == holdingData.to }?.PRICE
        if (currentPrice != null) {
            return doubleFromString(currentPrice.substring(2)) * holdingData.quantity
        }
        return 0.0
    }

    fun getChangePercentByHoldingData(holdingData: HoldingData): Double {
        val oldValue = holdingData.price * holdingData.quantity
        val selectedCoin = displayCoins.find { it.from == holdingData.from && it.to == holdingData.to }
        if (selectedCoin != null) {
            val newValue = doubleFromString(selectedCoin.PRICE.substring(2)) * holdingData.quantity
            return calculateChangePercent(oldValue, newValue)
        }
        return 0.0
    }

    fun getChangeValueByHoldingData(holdingData: HoldingData): Double {
        val oldValue = holdingData.price * holdingData.quantity
        val selectedCoin = displayCoins.find { it.from == holdingData.from && it.to == holdingData.to }
        if (selectedCoin != null) {
            val newValue = doubleFromString(selectedCoin.PRICE.substring(2)) * holdingData.quantity
            return newValue - oldValue
        }
        return 0.0
    }

    fun getImageUrlByHolding(holdingData: HoldingData) = displayCoins.find { it.from == holdingData.from }?.imgUrl ?: ""

    fun getCurrentPriceByHolding(holdingData: HoldingData) = displayCoins.find { it.from == holdingData.from }?.PRICE ?: ""

}