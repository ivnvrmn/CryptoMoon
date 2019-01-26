package com.rmnivnv.cryptomoon.ui.topcoins

import com.rmnivnv.cryptomoon.model.TopCoinData

/**
 * Created by rmnivnv on 02/09/2017.
 */
interface TopCoinsContract {

    interface View {
        fun updateList(topCoins: List<TopCoinData>)
        fun hideRefreshing()
        fun startCoinInfoActivity(name: String?)
    }

    interface Presenter {
        fun onStart()
        fun onStop()
        fun onCoinClicked(coin: TopCoinData)
        fun onAddCoinClicked(coin: TopCoinData, itemView: android.view.View)
        fun onSwiped()
    }
}