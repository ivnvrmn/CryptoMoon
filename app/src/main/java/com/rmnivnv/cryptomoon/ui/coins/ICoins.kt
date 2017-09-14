package com.rmnivnv.cryptomoon.ui.coins

import com.rmnivnv.cryptomoon.model.DisplayCoin

/**
 * Created by rmnivnv on 11/07/2017.
 */
interface ICoins {

    interface View {
        fun updateRecyclerView()
        fun hideRefreshing()
        fun enableSwipeToRefresh()
        fun disableSwipeToRefresh()
        fun startCoinInfoActivity(name: String?, to: String?)
        fun enableTotalHoldings()
        fun disableTotalHoldings()
        fun setTotalHoldingsValue(total: String)
        fun setTotalHoldingsChangePercent(percent: String)
        fun setTotalHoldingsChangePercentColor(color: Int)
        fun setTotalHoldingsChangeValue(value: String)
        fun setTotalHoldingsChangeValueColor(color: Int)
        fun setAllTimeProfitLossString(text: String)
        fun startHoldingsActivity()
    }

    interface Presenter {
        fun onCreate(coins: ArrayList<DisplayCoin>)
        fun onViewCreated()
        fun onStart()
        fun onStop()
        fun onDestroy()
        fun onSwipeUpdate()
        fun onCoinClicked(coin: DisplayCoin)
        fun onHoldingsClicked()
    }
}