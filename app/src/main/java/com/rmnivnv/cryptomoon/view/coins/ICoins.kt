package com.rmnivnv.cryptomoon.view.coins

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
    }

    interface Presenter {
        fun onCreate(component: CoinsComponent)
        fun onViewCreated(coins: ArrayList<DisplayCoin>)
        fun onStart()
        fun onDestroy()
        fun onSwipeUpdate()
        fun onCoinClicked(coin: DisplayCoin)
        fun onCoinLongClicked(coin: DisplayCoin): Boolean
    }
}