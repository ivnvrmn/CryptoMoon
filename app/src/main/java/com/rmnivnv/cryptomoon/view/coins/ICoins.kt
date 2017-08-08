package com.rmnivnv.cryptomoon.view.coins

import com.rmnivnv.cryptomoon.model.CoinBodyDisplay

/**
 * Created by rmnivnv on 11/07/2017.
 */
interface ICoins {

    interface View {
        fun updateRecyclerView()
        fun hideRefreshing()
        fun enableSwipeToRefresh()
        fun disableSwipeToRefresh()
        fun showCoinPopMenu(coin: CoinBodyDisplay)
    }

    interface Presenter {
        fun onCreate(component: CoinsComponent)
        fun onViewCreated(coins: ArrayList<CoinBodyDisplay>)
        fun onStart()
        fun onDestroy()
        fun onSwipeUpdate()
        fun onCoinClicked(coin: CoinBodyDisplay)
        fun onCoinLongClicked(coin: CoinBodyDisplay): Boolean
        fun onRemoveCoinClicked(coin: CoinBodyDisplay)
    }
}