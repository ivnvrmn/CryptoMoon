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
    }

    interface Presenter {
        fun onCreate(coins: ArrayList<DisplayCoin>)
        fun onViewCreated()
        fun onStart()
        fun onStop()
        fun onDestroy()
        fun onSwipeUpdate()
        fun onCoinClicked(coin: DisplayCoin)
    }
}