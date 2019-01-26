package com.rmnivnv.cryptomoon.ui.topcoins

import com.rmnivnv.cryptomoon.model.TopCoinData

/**
 * Created by rmnivnv on 02/09/2017.
 */
interface TopCoinsContract {

    interface View {
        fun updateList()
        fun hideRefreshing()
        fun startCoinInfoActivity(name: String?)
    }

    interface Presenter {
        fun onCreate(coins: ArrayList<TopCoinData>)
        fun onCoinClicked(coin: TopCoinData)
        fun onSwipeUpdate()
        fun onStart()
        fun onAddCoinClicked(coin: TopCoinData, itemView: android.view.View)
        fun onStop()
    }
}