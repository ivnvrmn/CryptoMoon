package com.rmnivnv.cryptomoon.view.topCoins

import com.rmnivnv.cryptomoon.model.TopCoinData

/**
 * Created by rmnivnv on 02/09/2017.
 */
interface ITopCoins {

    interface View {
        fun updateRecyclerView()
        fun hideRefreshing()
    }

    interface Presenter {
        fun onCreate(component: TopCoinsComponent, coins: ArrayList<TopCoinData>)
        fun onCoinClicked(coin: TopCoinData)
        fun onSwipeUpdate()
        fun onStart()
    }
}