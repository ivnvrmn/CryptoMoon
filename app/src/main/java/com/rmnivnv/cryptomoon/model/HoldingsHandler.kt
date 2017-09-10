package com.rmnivnv.cryptomoon.model

import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.utils.doubleFromString
import io.reactivex.schedulers.Schedulers

/**
 * Created by rmnivnv on 09/09/2017.
 */
class HoldingsHandler(private val db: CMDatabase) {

    init {
        db.displayCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .subscribe({ displayCoins = it })
    }

    private var displayCoins: List<DisplayCoin> = arrayListOf()

    fun getTotalValue(holdings: List<HoldingData>): Double {
        var totalValue = 0.0
        holdings.forEach { totalValue += (it.price * it.quantity) }
        return totalValue
    }

    fun getTotalChangePercent(holdings: List<HoldingData>): Double {
        val oldValue = getTotalValue(holdings)
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

    private fun calculateChangePercent(value1: Double, value2: Double) =
        if (value1 > value1) {
            (value2 - value1) / value2 * 100
        } else {
            (value2 - value1) / value1 * 100
        }
}