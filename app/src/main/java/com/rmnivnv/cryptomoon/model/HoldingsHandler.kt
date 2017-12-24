package com.rmnivnv.cryptomoon.model

import com.rmnivnv.cryptomoon.model.db.CMDatabase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by rmnivnv on 09/09/2017.
 */
class HoldingsHandler(private val db: CMDatabase) {

    init {
        db.coinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .subscribe({ coins = it })
        db.holdingsDao().getAllHoldings()
                .subscribeOn(Schedulers.io())
                .subscribe({ holdings = it })
    }

    private var coins: List<Coin> = arrayListOf()
    private var holdings: List<HoldingData> = arrayListOf()

    fun getTotalChangePercent(): Float {
        val oldValue = getTotalValueWithTradePrice()
        var newValue = 0f

        holdings.forEach {
            val quantity = it.quantity
            val fromList = coins.filter { (from) -> it.from == from }
            fromList.forEach {
                if (it.to == USD) {
                    newValue += quantity * it.priceRaw
                } else {
                    //todo calculate if not USD
                }
            }
        }
        return calculateChangePercent(oldValue, newValue)
    }

    private fun getTotalValueWithTradePrice(): Float {
        val sums: ArrayList<Float> = arrayListOf()
        holdings.forEach { sums.add(it.quantity * it.price) }
        return sums.sum()
    }

    private fun calculateChangePercent(value1: Float, value2: Float) =
            if (value1 > value1) (value2 - value1) / value2 * 100
            else (value2 - value1) / value1 * 100

    fun getTotalChangeValue() = getTotalValueWithCurrentPrice() - getTotalValueWithTradePrice()

    fun getTotalValueWithCurrentPrice(): Float {
        val sums: ArrayList<Float> = arrayListOf()
        holdings.forEach { (from, _, quantity) ->
            val currentPrice = coins.find { it.from == from }?.priceRaw
            if (currentPrice != null) {
                sums.add(quantity * currentPrice)
            }
        }
        return sums.sum()
    }

    fun getTotalValueWithCurrentPriceByHoldingData(holdingData: HoldingData): Float {
        val currentPrice = coins.find { it.from == holdingData.from && it.to == holdingData.to }?.priceRaw
        if (currentPrice != null) {
            return currentPrice * holdingData.quantity
        }
        return 0f
    }

    fun getChangePercentByHoldingData(holdingData: HoldingData): Float {
        val oldValue = holdingData.price * holdingData.quantity
        val selectedCoin = coins.find { it.from == holdingData.from && it.to == holdingData.to }
        if (selectedCoin != null) {
            val newValue = selectedCoin.priceRaw * holdingData.quantity
            return calculateChangePercent(oldValue, newValue)
        }
        return 0f
    }

    fun getChangeValueByHoldingData(holdingData: HoldingData): Float {
        val oldValue = holdingData.price * holdingData.quantity
        val selectedCoin = coins.find { it.from == holdingData.from && it.to == holdingData.to }
        if (selectedCoin != null) {
            val newValue = selectedCoin.priceRaw * holdingData.quantity
            return newValue - oldValue
        }
        return 0f
    }

    fun getImageUrlByHolding(holdingData: HoldingData) = coins.find { it.from == holdingData.from }?.imgUrl ?: ""

    fun getCurrentPriceByHolding(holdingData: HoldingData) = coins.find { it.from == holdingData.from }?.price ?: ""

    fun isThereSuchHolding(from: String?, to: String?) = holdings.find { it.from == from && it.to == to }

    fun removeHoldings(coins: List<Coin>) {
        coins.forEach { coin ->
            val holding = holdings.find { it.from == coin.from }
            if (holding != null) {
                Single.fromCallable { db.holdingsDao().deleteHolding(holding) }
                        .subscribeOn(Schedulers.io())
                        .subscribe()
            }
        }
    }

}