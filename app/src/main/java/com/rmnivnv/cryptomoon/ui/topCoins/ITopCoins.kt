package com.rmnivnv.cryptomoon.ui.topCoins

import com.rmnivnv.cryptomoon.model.TopCoinData

/**
 * Created by rmnivnv on 02/09/2017.
 */
interface ITopCoins {

    interface View {
        fun updateRecyclerView()
        fun hideRefreshing()
        fun startCoinInfoActivity(name: String?)
    }

    interface Presenter {
        fun onCreate(coins: ArrayList<TopCoinData>)
        fun onCoinClicked(coin: TopCoinData)
        fun onSwipeUpdate()
        fun onStart()
        fun onAddCoinClicked(coin: TopCoinData, itemView: android.view.View)
        fun onDestroy()
    }
}