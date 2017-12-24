package com.rmnivnv.cryptomoon.ui.coins

import com.rmnivnv.cryptomoon.model.Coin

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
        fun startAllocationsActivity()
        fun enableEmptyText()
        fun disableEmptyText()
    }

    interface Presenter {
        fun onCreate(coins: ArrayList<Coin>)
        fun onViewCreated()
        fun onStart()
        fun onStop()
        fun onSwipeUpdate()
        fun onCoinClicked(coin: Coin)
        fun onHoldingsClicked()
        fun onAllocationsClicked()
    }
}