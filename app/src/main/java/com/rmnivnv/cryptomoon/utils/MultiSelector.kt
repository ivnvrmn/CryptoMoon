package com.rmnivnv.cryptomoon.utils

import android.support.v7.widget.CardView
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.DisplayCoin
import com.rmnivnv.cryptomoon.model.rxbus.CoinsSelectedEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus

/**
 * Created by rmnivnv on 17/08/2017.
 */
class MultiSelector(val resProvider: ResourceProvider) {

    var atLeastOneIsSelected = false

    fun onClick(coin: DisplayCoin, card: CardView, coins: ArrayList<DisplayCoin>): Boolean {
        coin.selected = setBackgroundAndSelected(coin, card)
        RxBus.publish(CoinsSelectedEvent(checkIsSelected(coins)))
        return true
    }

    private fun setBackgroundAndSelected(coin: DisplayCoin, card: CardView) = if (coin.selected) {
            card.setBackgroundColor(resProvider.getColor(R.color.colorPrimaryDark))
            false
        } else {
            card.setBackgroundColor(resProvider.getColor(R.color.colorAccent))
            true
    }

    private fun checkIsSelected(coins: ArrayList<DisplayCoin>): Boolean {
        coins.forEach {
            if (it.selected) {
                atLeastOneIsSelected = true
                return true
            }
        }
        atLeastOneIsSelected = false
        return false
    }
}